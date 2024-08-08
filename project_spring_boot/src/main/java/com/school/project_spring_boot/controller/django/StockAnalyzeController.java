package com.school.project_spring_boot.controller.django;

import com.school.project_spring_boot.entity.django.PredictionResult;
import com.school.project_spring_boot.entity.django.TestResult;
import com.school.project_spring_boot.entity.stock.Stock;
import com.school.project_spring_boot.service.django.PredictionResultService;
import com.school.project_spring_boot.service.django.TestResultService;
import com.school.project_spring_boot.service.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StockAnalyzeController {

    private final TestResultService testResultService;
    private final PredictionResultService predictionResultService;
    private final StockService stockService;

    public StockAnalyzeController(TestResultService testResultService, PredictionResultService predictionResultService, StockService stockService) {
        this.testResultService = testResultService;
        this.predictionResultService = predictionResultService;
        this.stockService = stockService;
    }

    @GetMapping("/test-result/{isinCd}")
    public TestResult getLatestTestResult(@PathVariable String isinCd) {
        // ISIN 코드로 Stock 엔티티 조회
        Stock stock = stockService.getStockByIsin(isinCd);
        // stock_id로 TestResult 조회
        return testResultService.getLatestTestResultByStockId(stock.getId());
    }

    @GetMapping("/prediction-result/{isinCd}")
    public PredictionResult getLatestPredictionResult(@PathVariable String isinCd) {
        // ISIN 코드로 Stock 엔티티 조회
        Stock stock = stockService.getStockByIsin(isinCd);
        // stock_id로 PredictionResult 조회
        return predictionResultService.getLatestPredictionResultByStockId(stock.getId());
    }
}
