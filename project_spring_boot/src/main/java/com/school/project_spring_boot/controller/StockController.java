package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.StockDto;
import com.school.project_spring_boot.dto.response.ResponseDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.service.DailyStockDataService;
import com.school.project_spring_boot.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/fetch-all")
    public ResponseEntity<? super FetchStockDataResponseDto> fetchAllStocks() {
        try {
            ResponseEntity<? super FetchStockDataResponseDto> response = dailyStockDataService.fetchAndSaveAllStockData();
            return response;
        } catch (Exception e) {
            return FetchStockDataResponseDto.databaseError();
        }
    }

    @GetMapping("/public/{isinCd}/daily-data")
    public ResponseEntity<List<StockDto>> getStockData(@PathVariable String isinCd) {
        List<StockDto> stockData = stockService.getStockData(isinCd);
        return ResponseEntity.ok(stockData);
    }
}
