package com.school.project_spring_boot.service.django;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.entity.django.PredictionResult;
import com.school.project_spring_boot.entity.django.TestResult;
import com.school.project_spring_boot.entity.stock.Stock;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendation;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendationStock;
import com.school.project_spring_boot.repository.django.PredictionResultRepository;
import com.school.project_spring_boot.repository.django.TestResultRepository;
import com.school.project_spring_boot.repository.stock.StockRepository;
import com.school.project_spring_boot.repository.stock.WeeklyStockRecommendationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DjangoService {

    private final RestTemplate restTemplate;
    private final StockRepository stockRepository;
    private final WeeklyStockRecommendationRepository weeklyStockRecommendationRepository;
    private final TestResultRepository testResultRepository;
    private final PredictionResultRepository predictionResultRepository;

    private final ObjectMapper objectMapper;

    public DjangoService(RestTemplate restTemplate, StockRepository stockRepository,
                         WeeklyStockRecommendationRepository weeklyStockRecommendationRepository,
                         TestResultRepository testResultRepository,
                         PredictionResultRepository predictionResultRepository,
                         ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.stockRepository = stockRepository;
        this.weeklyStockRecommendationRepository = weeklyStockRecommendationRepository;
        this.testResultRepository = testResultRepository;
        this.predictionResultRepository = predictionResultRepository;
        this.objectMapper = objectMapper;
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> startTraining(String stockName, String startDate, int episodes, int windowSize) {
        String url = String.format("http://127.0.0.1:8000/api/train/?stock=%s&start_date=%s&episodes=%d&window_size=%d",
                stockName, startDate, episodes, windowSize);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> startTesting(String stockName, String startDate, int testRuns, int windowSize) {
        String url = String.format("http://127.0.0.1:8000/api/test/?stock=%s&start_date=%s&test_runs=%d&window_size=%d",
                stockName, startDate, testRuns, windowSize);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> predictAction(String stockName, int daysAgo, int windowSize) {
        String url = String.format("http://127.0.0.1:8000/api/predict/?stock=%s&days_ago=%d&window_size=%d",
                stockName, daysAgo, windowSize);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    public CompletableFuture<Void> testAndSaveWeeklyStocks() {
        WeeklyStockRecommendation latestWeeklyRecommendation = weeklyStockRecommendationRepository.findTopByOrderByStartDateDesc()
                .orElseThrow(() -> new RuntimeException("No weekly stock recommendations found"));

        // getStartDate()에서 가져온 문자열을 LocalDate로 변환
        String startDateStr = String.valueOf(latestWeeklyRecommendation.getStartDate());
        LocalDate startDate = LocalDate.parse(startDateStr);

        // 1년 전의 날짜 계산
        LocalDate oneYearBeforeStartDate = startDate.minusYears(1);

        // 다시 String으로 포맷팅
        String formattedDate = oneYearBeforeStartDate.format(DateTimeFormatter.ISO_DATE);

        List<CompletableFuture<Void>> futures = latestWeeklyRecommendation.getStocks().stream()
                .map(weeklyStock -> {
                    Stock stock = weeklyStock.getStock();
                    String stockName = stock.getSrtnCd();
                    return CompletableFuture.runAsync(() -> {
                        ResponseEntity<String> testResponse = restTemplate.getForEntity(
                                String.format("http://127.0.0.1:8000/api/test/?stock=%s&start_date=%s&test_runs=%d&window_size=%d",
                                        stockName, formattedDate, 10, 10), String.class);
                        saveTestResult(stock, testResponse.getBody(), formattedDate);
                    });
                }).toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Async
    public CompletableFuture<Void> predictAndSaveWeeklyStocks() {
        WeeklyStockRecommendation latestWeeklyRecommendation = weeklyStockRecommendationRepository.findTopByOrderByStartDateDesc()
                .orElseThrow(() -> new RuntimeException("No weekly stock recommendations found"));

        List<CompletableFuture<Void>> futures = latestWeeklyRecommendation.getStocks().stream()
                .map(weeklyStock -> {
                    Stock stock = weeklyStock.getStock();
                    String stockName = stock.getSrtnCd();
                    return CompletableFuture.runAsync(() -> {
                        ResponseEntity<String> predictResponse = restTemplate.getForEntity(
                                String.format("http://127.0.0.1:8000/api/predict/?stock=%s&days_ago=%d&window_size=%d",
                                        stockName, 0, 10), String.class);
                        savePredictionResult(stock, predictResponse.getBody());
                    });
                }).toList();

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private void saveTestResult(Stock stock, String testResult, String startDate) {
        try {
            JsonNode jsonNode = objectMapper.readTree(testResult);

            TestResult result = new TestResult();
            result.setStock(stock);
            result.setStartDate(LocalDate.parse(startDate));
            result.setAverageProfit(jsonNode.get("average_profit").asDouble());
            result.setTestRewards(jsonNode.get("test_rewards").toString());
            result.setTestProfits(jsonNode.get("test_profits").toString());

            testResultRepository.save(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePredictionResult(Stock stock, String predictResult) {
        try {
            JsonNode jsonNode = objectMapper.readTree(predictResult);

            PredictionResult result = new PredictionResult();
            result.setStock(stock);
            result.setAction(jsonNode.get("action").asText());
            result.setTargetDate(jsonNode.get("target_date").asText());

            predictionResultRepository.save(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
