package com.school.project_spring_boot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.dto.StockApiResponseDto;
import com.school.project_spring_boot.entity.DailyStockData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

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
public class PublicDataPortalService {

    private final StockService stockService;
    private final String serviceKey;

    public PublicDataPortalService(
            StockService stockService,
            @Value("${spring.publicData.serviceKey}") String serviceKey
    ) {
        this.stockService = stockService;
        this.serviceKey = serviceKey;
        System.out.println("Service Key: " + this.serviceKey); // 서비스 키 출력
    }

    private RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    public void fetchAndSaveStockData(String startDate, String endDate) {
        String encodedServiceKey;
        try {
            encodedServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8.toString());
            System.out.println("Encoded Service Key: " + encodedServiceKey); // 인코딩된 서비스 키 출력
        } catch (Exception e) {
            System.out.println("Error encoding service key: " + e.getMessage());
            return;
        }

        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo";
        int pageNo = 1;
        boolean hasMoreData = true;

        while (hasMoreData) {
            try {
                StringBuilder urlBuilder = new StringBuilder(urlTemplate);
                urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + encodedServiceKey);
                urlBuilder.append("&" + URLEncoder.encode("resultType", "UTF-8") + "=json");
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=100");
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + pageNo);
                urlBuilder.append("&" + URLEncoder.encode("beginBasDt", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8"));
                urlBuilder.append("&" + URLEncoder.encode("endBasDt", "UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8"));

                URL url = new URL(urlBuilder.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.connect();

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    ObjectMapper mapper = new ObjectMapper();
                    StockApiResponseDto stockApiResponseDto = mapper.readValue(inputStream, StockApiResponseDto.class);

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
