//package com.school.project_spring_boot.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.school.project_spring_boot.dto.IndexApiResponseDto;
//import com.school.project_spring_boot.entity.IndexData;
//import com.school.project_spring_boot.repository.IndexDataRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Service
//public class IndexService {
//
//    private final IndexDataRepository indexDataRepository;
//    private final String serviceKey;
//
//    public IndexService(IndexDataRepository indexDataRepository, @Value("${spring.publicData.serviceKey}") String serviceKey) {
//        this.indexDataRepository = indexDataRepository;
//        this.serviceKey = serviceKey;
//    }
//
//    public List<IndexData> fetchAndSaveLatestIndexData(String indexName) {
//        String encodedServiceKey;
//        try {
//            encodedServiceKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8.toString());
//        } catch (Exception e) {
//            throw new RuntimeException("Error encoding service key", e);
//        }
//
//        String urlTemplate = "https://apis.data.go.kr/1160100/service/GetMarketIndexInfoService/getMarketIndexInfo" +
//                "?serviceKey=" + encodedServiceKey +
//                "&resultType=json" +
//                "&idxNm=" + indexName +
//                "&numOfRows=1&pageNo=1"; // 최신 데이터만 가져오기 위해 페이지 크기를 1로 설정
//
//        try {
//            URL url = new URL(urlTemplate);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
//            httpURLConnection.setRequestProperty("Accept", "application/json");
//            httpURLConnection.connect();
//
//            int responseCode = httpURLConnection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                ObjectMapper mapper = new ObjectMapper();
//                IndexApiResponseDto apiResponse = mapper.readValue(httpURLConnection.getInputStream(), IndexApiResponseDto.class);
//                List<IndexApiResponseDto.Item> items = apiResponse.getResponse().getBody().getItems().getItem();
//
//                // 데이터 저장
//                for (IndexApiResponseDto.Item item : items) {
//                    IndexData indexData = new IndexData();
//                    indexData.setBasDt(item.getBasDt());
//                    indexData.setIdxNm(item.getIdxNm());
//                    indexData.setClpr(item.getClpr());
//                    indexData.setFltRt(item.getFltRt());
//                    indexDataRepository.save(indexData);
//                }
//
//                return indexDataRepository.findAll();
//            } else {
//                throw new RuntimeException("Failed to fetch index data: " + responseCode);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Exception occurred while fetching index data", e);
//        }
//    }
//}
