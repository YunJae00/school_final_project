package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.service.DailyStockDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final DailyStockDataService dailyStockDataService;

    public StockController(DailyStockDataService dailyStockDataService) {
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
}
