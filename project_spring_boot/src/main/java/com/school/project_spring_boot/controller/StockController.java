package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.StockDto;
import com.school.project_spring_boot.service.DailyStockDataService;
import com.school.project_spring_boot.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;
    private final DailyStockDataService dailyStockDataService;

    public StockController(StockService stockService, DailyStockDataService dailyStockDataService) {
        this.stockService = stockService;
        this.dailyStockDataService = dailyStockDataService;
    }

    @GetMapping("/fetch-all")
    public String fetchAllStocks() {
        try {
            dailyStockDataService.fetchAndSaveAllStockData();
            return "Data fetched and saved successfully.";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }

    @GetMapping("/public/{isinCd}/daily-data")
    public List<StockDto> getStockData(@PathVariable String isinCd) {
        return stockService.getStockData(isinCd);
    }
}
