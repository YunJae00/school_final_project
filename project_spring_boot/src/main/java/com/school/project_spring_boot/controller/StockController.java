package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockResponseDto;
import com.school.project_spring_boot.service.stock.DailyStockDataService;
import com.school.project_spring_boot.service.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;
    private final DailyStockDataService dailyStockDataService;

    public StockController(StockService stockService, DailyStockDataService dailyStockDataService) {
        this.stockService = stockService;
        this.dailyStockDataService = dailyStockDataService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<StockResponseDto>> searchStocks(@RequestParam String query) {
        List<StockResponseDto> stocks = stockService.searchStocks(query);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<StockResponseDto>> getWeeklyStocks(@RequestParam(required = false) String startDate) {
        LocalDate start;

        if (startDate == null || startDate.isEmpty()) {
            // startDate가 없으면 현재 날짜로 설정
            start = LocalDate.now();
        } else {
            start = LocalDate.parse(startDate);
        }

        List<StockResponseDto> weeklyStocks = stockService.getWeeklyStocks(start);
        return ResponseEntity.ok(weeklyStocks);
    }

    @PostMapping("/weekly/{stockId}")
    public ResponseEntity<Void> addStockToWeekly(@RequestParam String startDate, @PathVariable Long stockId) {
        LocalDate start = LocalDate.parse(startDate);
        stockService.addStockToWeekly(start, stockId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/weekly/{stockId}")
    public ResponseEntity<Void> removeStockFromWeekly(@RequestParam String startDate, @PathVariable Long stockId) {
        LocalDate start = LocalDate.parse(startDate);
        stockService.removeStockFromWeekly(start, stockId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/fetch-by-code-and-date")
    public ResponseEntity<? super FetchStockDataResponseDto> fetchStockDataByCodeAndDate(
            @RequestBody StockDataRequestDto requestBody
    ) {
        try {
            // 오늘 날짜 계산
            LocalDate today = LocalDate.now();
            // 1년 전 날짜 계산
            LocalDate oneYearAgo = today.minusYears(1);

            // 날짜 포맷 설정 (yyyyMMdd)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // 클라이언트가 날짜를 보내지 않았을 때만 자동으로 날짜 설정
            if (requestBody.getStartDate() == null || requestBody.getStartDate().isEmpty()) {
                requestBody.setStartDate(oneYearAgo.format(formatter));
            }
            if (requestBody.getEndDate() == null || requestBody.getEndDate().isEmpty()) {
                requestBody.setEndDate(today.format(formatter));
            }

            // 데이터 가져오기 및 저장
            ResponseEntity<? super FetchStockDataResponseDto> response = dailyStockDataService.fetchAndSaveStockDataByCodeAndDate(requestBody);
            return response;
        } catch (Exception e) {
            return FetchStockDataResponseDto.databaseError();
        }
    }

    @GetMapping("/public/daily-data")
    public ResponseEntity<List<StockDataResponseDto>> getStockDataByDateRange(
            @RequestParam String isinCd,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        List<StockDataResponseDto> stockData = stockService.getStockDataByDateRange(isinCd, start, end);
        return ResponseEntity.ok(stockData);
    }

    @PostMapping("/fetch-all-stocks-info")
    public ResponseEntity<String> fetchAndSaveAllStocksInfo() {
        try {
            stockService.fetchAndSaveAllStocksInfo();
            return ResponseEntity.ok("All stocks info fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching and saving all stocks info.");
        }
    }

}
