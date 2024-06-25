package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.DailyStockDataDto;
import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.service.StockService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stocks")
    public List<DailyStockDataDto> getStockData(
            @RequestParam String isinCd,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailyStockData> stockDataList = stockService.getStockData(isinCd, startDate, endDate);
        return stockDataList.stream()
                .map(data -> new DailyStockDataDto(
                        data.getBasDt(),
                        data.getClpr(),
                        data.getHipr(),
                        data.getLopr(),
                        data.getMkp(),
                        data.getVs(),
                        data.getFltRt(),
                        data.getTrqu(),
                        data.getTrPrc(),
                        data.getLstgStCnt(),
                        data.getMrktTotAmt()
                ))
                .collect(Collectors.toList());
    }
}
