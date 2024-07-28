package com.school.project_spring_boot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.dto.StockApiResponseDto;
import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.entity.DailyStockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyStockDataService {

    private static final Logger logger = LoggerFactory.getLogger(DailyStockDataService.class);

    private final StockService stockService;
    private final String serviceKey;

    public DailyStockDataService(
            StockService stockService,
            @Value("${spring.publicData.serviceKey}") String serviceKey
    ) {
        this.stockService = stockService;
        this.serviceKey = serviceKey;
    }

    public ResponseEntity<? super FetchStockDataResponseDto> fetchAndSaveStockDataByCodeAndDate(StockDataRequestDto requestDto) {
        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
        int pageNo = 1;
        int totalCount = 0;
        int numOfRows = 10; // Default rows per page, adjust if needed

        try {
            do {
                URL url = new URL(
                        urlTemplate
                                + "?serviceKey="
                                + serviceKey
                                + "&resultType=json"
                                + "&beginBasDt=" + requestDto.getStartDate()
                                + "&endBasDt=" + requestDto.getEndDate()
                                + "&isinCd=" + requestDto.getIsinCd()
                                + "&pageNo=" + pageNo
                );

                System.out.println("Request URL: " + url.toString());

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

                    System.out.println("Response JSON: " + jsonResponse);

                    if (jsonResponse.startsWith("<")) {
                        logger.error("Received non-JSON response: {}", jsonResponse);
                        return FetchStockDataResponseDto.databaseError();
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    StockApiResponseDto stockApiResponseDto = mapper.readValue(jsonResponse, StockApiResponseDto.class);

                    if (stockApiResponseDto.getResponse() != null
                            && stockApiResponseDto.getResponse().getBody() != null
                            && stockApiResponseDto.getResponse().getBody().getItems() != null) {
                        List<StockApiResponseDto.Item> items = stockApiResponseDto.getResponse().getBody().getItems().getItem();
                        totalCount = stockApiResponseDto.getResponse().getBody().getTotalCount();
                        numOfRows = stockApiResponseDto.getResponse().getBody().getNumOfRows();

                        System.out.println("Number of items received: " + items.size());
                        if (!items.isEmpty()) {
                            for (StockApiResponseDto.Item item : items) {
                                List<DailyStockData> dailyStockDataList = new ArrayList<>();
                                DailyStockData dailyStockData = getDailyStockData(item);

                                dailyStockDataList.add(dailyStockData);

                                String mrktCls = item.getMrktCtg() != null ? item.getMrktCtg() : "Unknown";
                                stockService.saveStockData(item.getIsinCd(), item.getSrtnCd(), item.getItmsNm(), mrktCls, dailyStockDataList);

                                // Log each item being processed
                                System.out.println("Processed item: " + item.getBasDt() + " - " + item.getItmsNm());
                            }
                        }
                    } else {
                        return FetchStockDataResponseDto.databaseError();
                    }
                    httpURLConnection.disconnect();
                } else {
                    return FetchStockDataResponseDto.databaseError();
                }

                pageNo++;
            } while ((pageNo - 1) * numOfRows < totalCount);

        } catch (Exception e) {
            return FetchStockDataResponseDto.databaseError();
        }
        return FetchStockDataResponseDto.success();
    }

    private static DailyStockData getDailyStockData(StockApiResponseDto.Item item) {
        DailyStockData dailyStockData = new DailyStockData();
        dailyStockData.setBasDt(LocalDate.parse(item.getBasDt(), DateTimeFormatter.BASIC_ISO_DATE));
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
        return dailyStockData;
    }
}
