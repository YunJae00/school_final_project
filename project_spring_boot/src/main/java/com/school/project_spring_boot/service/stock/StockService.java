package com.school.project_spring_boot.service.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockApiResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockWithDataResponseDto;
import com.school.project_spring_boot.entity.stock.DailyStockData;
import com.school.project_spring_boot.entity.stock.Stock;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendation;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendationStock;
import com.school.project_spring_boot.repository.stock.DailyStockDataRepository;
import com.school.project_spring_boot.repository.stock.StockRepository;
import com.school.project_spring_boot.repository.stock.WeeklyStockRecommendationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;
    private final DailyStockDataRepository dailyStockDataRepository;
    private final WeeklyStockRecommendationRepository weeklyStockRecommendationRepository;
    private final String serviceKey;

    public StockService(
            StockRepository stockRepository,
            DailyStockDataRepository dailyStockDataRepository,
            WeeklyStockRecommendationRepository weeklyStockRecommendationRepository,
            @Value("${spring.publicData.serviceKey}") String serviceKey) {
        this.stockRepository = stockRepository;
        this.dailyStockDataRepository = dailyStockDataRepository;
        this.weeklyStockRecommendationRepository = weeklyStockRecommendationRepository;
        this.serviceKey = serviceKey;
    }

    // 주식 검색
    public List<StockResponseDto> searchStocks(String query) {
        return stockRepository.findByItmsNmContaining(query).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 주차별 주식 리스트 가져오기
    public List<StockResponseDto> getWeeklyStocks(LocalDate startDate) {
        Optional<WeeklyStockRecommendation> weeklyStocksOpt = weeklyStockRecommendationRepository.findByStartDate(startDate);
        if (weeklyStocksOpt.isPresent()) {
            WeeklyStockRecommendation weeklyStocks = weeklyStocksOpt.get();
            return weeklyStocks.getStocks().stream()
                    .map(stock -> convertToDto(stock.getStock()))
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    // 주차별 주식 추가
    public void addStockToWeekly(LocalDate startDate, Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("주식을 찾을 수 없습니다."));

        WeeklyStockRecommendation weeklyStocks = weeklyStockRecommendationRepository.findByStartDate(startDate)
                .orElseGet(() -> {
                    WeeklyStockRecommendation newRecommendation = new WeeklyStockRecommendation();
                    newRecommendation.setStartDate(startDate);
                    newRecommendation.setEndDate(startDate.plusDays(6));
                    return newRecommendation;
                });

        if (weeklyStocks.getStocks().stream().noneMatch(ws -> ws.getStock().equals(stock))) {
            WeeklyStockRecommendationStock weeklyStockRecommendationStock = new WeeklyStockRecommendationStock();
            weeklyStockRecommendationStock.setStock(stock);
            weeklyStockRecommendationStock.setWeeklyStockRecommendation(weeklyStocks);

            weeklyStocks.getStocks().add(weeklyStockRecommendationStock);
            weeklyStockRecommendationRepository.save(weeklyStocks);
        }
    }

    // 주차별 주식 삭제
    public void removeStockFromWeekly(LocalDate startDate, Long stockId) {
        WeeklyStockRecommendation weeklyStocks = weeklyStockRecommendationRepository.findByStartDate(startDate)
                .orElseThrow(() -> new RuntimeException("해당 주차의 추천 목록을 찾을 수 없습니다."));
        weeklyStocks.getStocks().removeIf(stock -> stock.getStock().getId().equals(stockId));
        weeklyStockRecommendationRepository.save(weeklyStocks);
    }

    // 주차별 주식 데이터 저장
    public void saveWeeklyStockData(LocalDate startDate) {
        WeeklyStockRecommendation weeklyStocks = weeklyStockRecommendationRepository.findByStartDate(startDate)
                .orElseThrow(() -> new RuntimeException("해당 주차의 추천 목록을 찾을 수 없습니다."));

        LocalDate oneYearAgo = startDate.minusYears(1);
        LocalDate endDate = startDate.plusDays(6);

        for (WeeklyStockRecommendationStock stockRec : weeklyStocks.getStocks()) {
            Stock stock = stockRec.getStock();
            fetchAndSaveStockDataByCodeAndDate(stock.getIsinCd(), oneYearAgo, endDate);
        }
    }

    // 다수의 주식 데이터 가져오기 및 저장
    public void fetchMultipleStockData(List<StockDataRequestDto> stockRequests) {
        for (StockDataRequestDto request : stockRequests) {
            fetchAndSaveStockDataByCodeAndDate(request.getIsinCd(), LocalDate.parse(request.getStartDate()), LocalDate.parse(request.getEndDate()));
        }
    }

    // 전체 주식 정보 가져오기 및 저장
    @Transactional
    public void fetchAndSaveAllStocksInfo() {
        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 오늘 날짜를 "yyyyMMdd" 형식으로 포맷
        int pageNo = 1;
        int numOfRows = 100;

        try {
            Set<String> existingIsinCodes = stockRepository.findAllIsinCodes();
            while (true) {
                URL url = new URL(
                        urlTemplate
                                + "?serviceKey="
                                + serviceKey
                                + "&resultType=json"
                                + "&beginBasDt=" + today
                                + "&endBasDt=" + today
                                + "&pageNo=" + pageNo
                                + "&numOfRows=" + numOfRows
                );

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                    }
                    rd.close();

                    String jsonResponse = response.toString();

                    ObjectMapper mapper = new ObjectMapper();
                    StockApiResponseDto stockApiResponseDto = mapper.readValue(jsonResponse, StockApiResponseDto.class);

                    if (stockApiResponseDto.getResponse() != null
                            && stockApiResponseDto.getResponse().getBody() != null
                            && stockApiResponseDto.getResponse().getBody().getItems() != null) {
                        List<StockApiResponseDto.Item> items = stockApiResponseDto.getResponse().getBody().getItems().getItem();

                        if (items.isEmpty()) {
                            break; // 더 이상 가져올 데이터가 없으면 루프 종료
                        }

                        for (StockApiResponseDto.Item item : items) {
                            if (!existingIsinCodes.contains(item.getIsinCd())) {
                                String mrktCls = item.getMrktCtg() != null ? item.getMrktCtg() : "Unknown";
                                saveStockIfNotExist(item.getIsinCd(), item.getSrtnCd(), item.getItmsNm(), mrktCls);
                            }
                        }
                    } else {
                        break; // 데이터를 더 가져올 수 없는 경우 루프 종료
                    }

                    httpURLConnection.disconnect();
                } else {
                    break; // 에러 발생 시 루프 종료
                }

                pageNo++;
            }

        } catch (Exception e) {
            logger.error("Error while fetching stock data: ", e);
        }
    }

    // 최신 주차별 주식 데이터 가져오기
    public List<StockWithDataResponseDto> getLatestWeeklyStocksData() {
        WeeklyStockRecommendation latestWeeklyStocks = getLatestWeeklyStocks();
        if (latestWeeklyStocks == null) {
            return new ArrayList<>();
        }

        List<StockWithDataResponseDto> responseList = new ArrayList<>();
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(1);

        for (WeeklyStockRecommendationStock stockRec : latestWeeklyStocks.getStocks()) {
            Stock stock = stockRec.getStock();
            List<StockDataResponseDto> stockData = getStockDataByDateRange(stock.getIsinCd(), startDate, endDate);

            StockWithDataResponseDto stockWithData = new StockWithDataResponseDto(
                    stock.getIsinCd(),
                    stock.getItmsNm(),
                    stockData
            );
            responseList.add(stockWithData);
        }

        return responseList;
    }

    // 주식 데이터 가져오기 및 저장
    public ResponseEntity<? super FetchStockDataResponseDto> fetchStockDataByCodeAndDate(StockDataRequestDto requestBody) {
        try {
            LocalDate today = LocalDate.now();
            LocalDate oneYearAgo = today.minusYears(1);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            String startDate = requestBody.getStartDate() != null && !requestBody.getStartDate().isEmpty()
                    ? requestBody.getStartDate()
                    : oneYearAgo.format(formatter);

            String endDate = requestBody.getEndDate() != null && !requestBody.getEndDate().isEmpty()
                    ? requestBody.getEndDate()
                    : today.format(formatter);

            fetchAndSaveStockDataByCodeAndDate(requestBody.getIsinCd(), LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter));

            return FetchStockDataResponseDto.success();
        } catch (Exception e) {
            logger.error("Error while fetching and saving stock data: ", e);
            return FetchStockDataResponseDto.databaseError();
        }
    }

    // 주식 데이터 범위에 따라 가져오기
    private List<StockDataResponseDto> getStockDataByDateRange(String isinCd, LocalDate startDate, LocalDate endDate) {
        Optional<Stock> optionalStock = stockRepository.findByIsinCd(isinCd);
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            return dailyStockDataRepository.findByStockAndBasDtBetween(stock, startDate, endDate).stream()
                    .map(data -> new StockDataResponseDto(
                            data.getBasDt(),
                            data.getClpr(),
                            data.getHipr(),
                            data.getLopr(),
                            data.getMkp(),
                            data.getVs(),
                            data.getFltRt(),
                            data.getTrqu(),
                            data.getTrPrc(),
                            data.getLstgStCnt(),
                            data.getMrktTotAmt()
                    ))
                    .collect(Collectors.toList());
        } else {
            throw new RuntimeException("Stock not found");
        }
    }

    // 주식 데이터를 가져와 저장하는 메서드
    private void fetchAndSaveStockDataByCodeAndDate(String isinCd, LocalDate startDate, LocalDate endDate) {
        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
        String start = startDate.format(DateTimeFormatter.BASIC_ISO_DATE); // "yyyyMMdd" 형식으로 포맷
        String end = endDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        int pageNo = 1;
        int numOfRows = 100;

        try {
            while (true) {
                URL url = new URL(
                        urlTemplate
                                + "?serviceKey=" + serviceKey
                                + "&resultType=json"
                                + "&beginBasDt=" + start
                                + "&endBasDt=" + end
                                + "&isinCd=" + isinCd
                                + "&pageNo=" + pageNo
                                + "&numOfRows=" + numOfRows
                );

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                    }
                    rd.close();

                    String jsonResponse = response.toString();

                    ObjectMapper mapper = new ObjectMapper();
                    StockApiResponseDto stockApiResponseDto = mapper.readValue(jsonResponse, StockApiResponseDto.class);

                    if (stockApiResponseDto.getResponse() != null
                            && stockApiResponseDto.getResponse().getBody() != null
                            && stockApiResponseDto.getResponse().getBody().getItems() != null) {
                        List<StockApiResponseDto.Item> items = stockApiResponseDto.getResponse().getBody().getItems().getItem();

                        if (items.isEmpty()) {
                            break; // 더 이상 가져올 데이터가 없으면 루프 종료
                        }

                        for (StockApiResponseDto.Item item : items) {
                            saveStockData(isinCd, item);
                        }
                    } else {
                        break; // 데이터를 더 가져올 수 없는 경우 루프 종료
                    }

                    httpURLConnection.disconnect();
                } else {
                    break; // 에러 발생 시 루프 종료
                }

                pageNo++;
            }

        } catch (Exception e) {
            logger.error("Error while fetching stock data: ", e);
        }
    }

    // 주식 데이터를 저장하는 메서드
    private void saveStockData(String isinCd, StockApiResponseDto.Item item) {
        Stock stock = stockRepository.findByIsinCd(isinCd)
                .orElseThrow(() -> new RuntimeException("주식을 찾을 수 없습니다."));

        LocalDate basDt = LocalDate.parse(item.getBasDt(), DateTimeFormatter.BASIC_ISO_DATE);

        // 기존 데이터가 존재하는지 확인하여 중복 삽입 방지
        boolean exists = dailyStockDataRepository.existsByStockAndBasDt(stock, basDt);
        if (!exists) {
            DailyStockData dailyStockData = new DailyStockData();
            dailyStockData.setStock(stock);
            dailyStockData.setBasDt(basDt);
            dailyStockData.setClpr(new BigDecimal(item.getClpr()));
            dailyStockData.setHipr(new BigDecimal(item.getHipr()));
            dailyStockData.setLopr(new BigDecimal(item.getLopr()));
            dailyStockData.setMkp(new BigDecimal(item.getMkp()));
            dailyStockData.setVs(new BigDecimal(item.getVs()));
            dailyStockData.setFltRt(new BigDecimal(item.getFltRt()));
            dailyStockData.setTrqu(Long.parseLong(item.getTrqu()));
            dailyStockData.setTrPrc(new BigDecimal(item.getTrPrc()));
            dailyStockData.setLstgStCnt(Long.parseLong(item.getLstgStCnt()));
            dailyStockData.setMrktTotAmt(Long.parseLong(item.getMrktTotAmt()));

            dailyStockDataRepository.save(dailyStockData);
        }
    }

    // 주식이 존재하지 않으면 저장하는 메서드
    public Stock saveStockIfNotExist(String isinCd, String srtnCd, String itmsNm, String mrktCls) {
        Optional<Stock> optionalStock = stockRepository.findByIsinCd(isinCd);
        if (optionalStock.isEmpty()) {
            Stock stock = new Stock();
            stock.setIsinCd(isinCd);
            stock.setSrtnCd(srtnCd);
            stock.setItmsNm(itmsNm);
            stock.setMrktCls(mrktCls);
            stockRepository.save(stock);
            logger.info("Stock saved: {}", itmsNm);
            return stock;
        }
        return optionalStock.get();
    }

    // 최신 주차별 추천 목록 가져오기
    private WeeklyStockRecommendation getLatestWeeklyStocks() {
        return weeklyStockRecommendationRepository.findTopByOrderByStartDateDesc().orElse(null);
    }

    // DTO 변환 메서드
    private StockResponseDto convertToDto(Stock stock) {
        return new StockResponseDto(stock.getId(), stock.getItmsNm(), stock.getIsinCd());
    }

    public Stock getStockByIsin(String isinCd) {
        return stockRepository.findByIsinCd(isinCd).orElseThrow(() -> new RuntimeException("Stock not found with ISIN: " + isinCd));
    }
}
