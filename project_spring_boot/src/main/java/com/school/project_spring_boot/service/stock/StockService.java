package com.school.project_spring_boot.service.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.dto.response.stock.StockApiResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockDataResponseDto;
import com.school.project_spring_boot.entity.stock.DailyStockData;
import com.school.project_spring_boot.entity.stock.Stock;
import com.school.project_spring_boot.repository.DailyStockDataRepository;
import com.school.project_spring_boot.repository.StockRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;
    private final DailyStockDataRepository dailyStockDataRepository;
    private final String serviceKey;

    // 로컬 캐시 추가
    private final Map<String, Stock> stockCache = new HashMap<>();

    public StockService(
            StockRepository stockRepository,
            DailyStockDataRepository dailyStockDataRepository,
            @Value("${spring.publicData.serviceKey}") String serviceKey) {
        this.stockRepository = stockRepository;
        this.dailyStockDataRepository = dailyStockDataRepository;
        this.serviceKey = serviceKey;
    }

    public void saveStockData(String isinCd, String srtnCd, String itmsNm, String mrktCls, List<DailyStockData> dailyStockDataList) {
        // 주식이 이미 존재하는지 확인
        if (stockRepository.existsByIsinCd(isinCd)) {
            // 이미 존재하면 저장하지 않음
            return;
        }

        // 주식이 존재하지 않으면 새로 저장
        Stock stock = new Stock();
        stock.setIsinCd(isinCd);
        stock.setSrtnCd(srtnCd);
        stock.setItmsNm(itmsNm);
        stock.setMrktCls(mrktCls);
        stockRepository.save(stock);

        // DailyStockData가 존재하는 경우에만 저장
        if (dailyStockDataList != null) {
            for (DailyStockData dailyStockData : dailyStockDataList) {
                if (!dailyStockDataRepository.existsByStockAndBasDt(stock, dailyStockData.getBasDt())) {
                    dailyStockData.setStock(stock);
                    dailyStockDataRepository.save(dailyStockData);
                }
            }
        }
    }


    public List<StockDataResponseDto> getStockDataByDateRange(String isinCd, LocalDate startDate, LocalDate endDate) {
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

    @Transactional
    public void fetchAndSaveAllStocksInfo() {
        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // 오늘 날짜를 "yyyyMMdd" 형식으로 포맷
        int pageNo = 1;
        int numOfRows = 100; // 한 페이지에 최대 데이터를 가져오도록 설정

        try {
            // 모든 기존 주식 ISIN 코드를 한 번에 조회
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
                            // 이미 저장된 ISIN 코드인지 확인하고, 존재하지 않는 경우에만 저장
                            if (!existingIsinCodes.contains(item.getIsinCd())) {
                                String mrktCls = item.getMrktCtg() != null ? item.getMrktCtg() : "Unknown";
                                saveStockData(item.getIsinCd(), item.getSrtnCd(), item.getItmsNm(), mrktCls, null);
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
}
