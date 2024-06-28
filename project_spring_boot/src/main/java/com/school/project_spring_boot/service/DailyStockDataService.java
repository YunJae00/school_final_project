package com.school.project_spring_boot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.dto.StockApiResponseDto;
import com.school.project_spring_boot.entity.DailyStockData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyStockDataService {

    private final StockService stockService;
    private final String serviceKey;

    public DailyStockDataService(
            StockService stockService,
            @Value("${spring.publicData.serviceKey}") String serviceKey
    ) {
        this.stockService = stockService;
        this.serviceKey = serviceKey;
        System.out.println("Service Key: " + this.serviceKey);
    }

    public void fetchAndSaveAllStockData() {

        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
        int pageNo = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            try {
                URL url = new URL(
                        urlTemplate
                        + "?serviceKey="
                        + serviceKey
                        + "&resultType="
                        + "json"
                        + "&pageNo="
                        + pageNo
                        + "&numOfRows="
                        + "100"
                );

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.connect();

                System.out.println(url);

                int responseCode = httpURLConnection.getResponseCode();
                System.out.println("Response code: " + responseCode); // 응답 코드 출력

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

                    if (stockApiResponseDto.getResponse() != null && stockApiResponseDto.getResponse().getBody() != null && stockApiResponseDto.getResponse().getBody().getItems() != null) {
                        List<StockApiResponseDto.Item> items = stockApiResponseDto.getResponse().getBody().getItems().getItem();
                        if (items.isEmpty()) {
                            hasMoreData = false;
                        } else {
                            for (StockApiResponseDto.Item item : items) {
                                List<DailyStockData> dailyStockDataList = new ArrayList<>();
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

                                dailyStockDataList.add(dailyStockData);

                                String mrktCls = item.getMrktCtg() != null ? item.getMrktCtg() : "Unknown";
                                stockService.saveStockData(item.getIsinCd(), item.getSrtnCd(), item.getItmsNm(), mrktCls, dailyStockDataList);
                            }
                            pageNo++;
                        }
                    } else {
                        System.out.println("No more data.");
                        hasMoreData = false;
                    }
                } else {
                    System.out.println("API call failed with status code: " + responseCode);
                    hasMoreData = false;
                }
                httpURLConnection.disconnect();
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                System.out.println("HTTP error occurred: " + e.getMessage());
                hasMoreData = false;
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.getMessage());
                hasMoreData = false;
            }
        }
    }
}
