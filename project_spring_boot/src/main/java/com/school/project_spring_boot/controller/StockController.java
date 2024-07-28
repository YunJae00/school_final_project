package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.StockDto;
import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockDataResponseDto;
import com.school.project_spring_boot.service.DailyStockDataService;
import com.school.project_spring_boot.service.StockService;
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

    @GetMapping("/{isinCd}/daily-data")
    public ResponseEntity<List<StockDto>> getStockData(@PathVariable String isinCd) {
        List<StockDto> stockData = stockService.getStockData(isinCd);
        return ResponseEntity.ok(stockData);
    }

    @PostMapping("/fetch-by-code-and-date")
    public ResponseEntity<? super FetchStockDataResponseDto> fetchStockDataByCodeAndDate(
            @RequestBody StockDataRequestDto requestBody
    ) {
        try {
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

}
