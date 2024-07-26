package com.school.project_spring_boot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.dto.StockApiResponseDto;
import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.entity.DailyStockData;
import lombok.RequiredArgsConstructor;
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
        try {
            URL url = new URL(
                    urlTemplate
                            + "?serviceKey="
                            + serviceKey
                            + "&resultType=json"
                            + "&beginBasDt=" + requestDto.getStartDate()
                            + "&endBasDt=" + requestDto.getEndDate()
                            + "&isinCd=" + requestDto.getIsinCd()
            );

            logger.info("Request URL: {}", url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            logger.info("Response code: {}", responseCode);

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
                logger.debug("API Response: {}", jsonResponse);

                if (jsonResponse.startsWith("<")) {
                    logger.error("Received non-JSON response: {}", jsonResponse);
                    return FetchStockDataResponseDto.databaseError();
                }

                ObjectMapper mapper = new ObjectMapper();
                StockApiResponseDto stockApiResponseDto = mapper.readValue(jsonResponse, StockApiResponseDto.class);

                if (stockApiResponseDto.getResponse() != null && stockApiResponseDto.getResponse().getBody() != null && stockApiResponseDto.getResponse().getBody().getItems() != null) {
                    List<StockApiResponseDto.Item> items = stockApiResponseDto.getResponse().getBody().getItems().getItem();
                    if (!items.isEmpty()) {
                        for (StockApiResponseDto.Item item : items) {
                            List<DailyStockData> dailyStockDataList = new ArrayList<>();
                            DailyStockData dailyStockData = getDailyStockData(item);

                            dailyStockDataList.add(dailyStockData);

                            String mrktCls = item.getMrktCtg() != null ? item.getMrktCtg() : "Unknown";
                            stockService.saveStockData(item.getIsinCd(), item.getSrtnCd(), item.getItmsNm(), mrktCls, dailyStockDataList);
                        }
                    }
                }
            } else {
                return FetchStockDataResponseDto.databaseError();
            }
            httpURLConnection.disconnect();
        } catch (Exception e) {
            logger.error("Exception occurred: ", e);
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
