package com.school.project_spring_boot.service.django;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project_spring_boot.entity.django.PredictionResult;
import com.school.project_spring_boot.entity.django.TestResult;
import com.school.project_spring_boot.entity.stock.Stock;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendation;
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
import java.time.temporal.ChronoUnit;
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

    @Async
    public CompletableFuture<Void> testAndSaveWeeklyStocks() {
        WeeklyStockRecommendation latestWeeklyRecommendation = weeklyStockRecommendationRepository.findTopByOrderByStartDateDesc()
                .orElseThrow(() -> new RuntimeException("No weekly stock recommendations found"));

        // getStartDate()에서 가져온 문자열을 LocalDate로 변환
        String startDateStr = String.valueOf(latestWeeklyRecommendation.getStartDate());
        LocalDate startDate = LocalDate.parse(startDateStr);

        // 테스트할 때 사용할 날짜 (5년 전)
        LocalDate fiveYearsBeforeStartDate = startDate.minusYears(5);

        // 저장할 때 사용할 날짜 (1년 전)
        LocalDate oneYearBeforeStartDate = startDate.minusYears(1);

        // 포맷팅
        String testFormattedDate = fiveYearsBeforeStartDate.format(DateTimeFormatter.ISO_DATE);
        String saveFormattedDate = oneYearBeforeStartDate.format(DateTimeFormatter.ISO_DATE);

        List<CompletableFuture<Void>> futures = latestWeeklyRecommendation.getStocks().stream()
                .map(weeklyStock -> {
                    Stock stock = weeklyStock.getStock();
                    String stockName = stock.getSrtnCd();

                    // 개별적으로 startTesting 메서드를 호출하여 비동기 작업 수행
                    return startTesting(stockName, testFormattedDate, 10, 10)
                            // **저장할 때는 1년 전의 날짜를 사용**
                            .thenAccept(response -> saveTestResult(stock, response.getBody(), saveFormattedDate));
                })
                .toList();

        // 모든 비동기 작업이 완료될 때까지 기다림
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

                    // 개별적으로 predictAction 메서드를 호출하여 비동기 작업 수행
                    return predictAction(stockName, 0, 10)
                            .thenAccept(response -> savePredictionResult(stock, response.getBody()));
                })
                .toList();

        // 모든 비동기 작업이 완료될 때까지 기다림
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    private void saveTestResult(Stock stock, String testResult, String startDate) {
        try {
            System.out.println("Received test result: " + testResult);  // 로그 추가
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
