package com.school.project_spring_boot.controller.stock;

import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockWithDataResponseDto;
import com.school.project_spring_boot.service.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<StockResponseDto>> searchStocks(@RequestParam String query) {
        List<StockResponseDto> stocks = stockService.searchStocks(query);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<StockResponseDto>> getWeeklyStocks(@RequestParam String startDate) {
        List<StockResponseDto> weeklyStocks = stockService.getWeeklyStocks(LocalDate.parse(startDate));
        return ResponseEntity.ok(weeklyStocks);
    }

    @PostMapping("/weekly/{stockId}")
    public ResponseEntity<Void> addStockToWeekly(@RequestParam String startDate, @PathVariable Long stockId) {
        stockService.addStockToWeekly(LocalDate.parse(startDate), stockId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/weekly/{stockId}")
    public ResponseEntity<Void> removeStockFromWeekly(@RequestParam String startDate, @PathVariable Long stockId) {
        stockService.removeStockFromWeekly(LocalDate.parse(startDate), stockId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/weekly/save")
    public ResponseEntity<String> saveWeeklyStockData(@RequestParam String startDate) {
        try {
            stockService.saveWeeklyStockData(LocalDate.parse(startDate));
            return ResponseEntity.ok("주간 주식 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("주간 주식 데이터를 저장하는 중 오류가 발생했습니다.");
        }
    }

    // admin 페이지에서 주식 10개를 넘겨서 저장하는 controller
    @PostMapping("/fetch-multiple-stocks")
    public ResponseEntity<String> fetchMultipleStockDataByCodeAndDate(@RequestBody List<StockDataRequestDto> stockRequests) {
        try {
            stockService.fetchMultipleStockData(stockRequests);
            return ResponseEntity.ok("All stock data successfully downloaded and saved.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while downloading stock data.");
        }
    }

    // 전체 stock data 채우 (가장 먼저 해야할거)
    @PostMapping("/fetch-all-stocks-info")
    public ResponseEntity<String> fetchAndSaveAllStocksInfo() {
        try {
            stockService.fetchAndSaveAllStocksInfo();
            return ResponseEntity.ok("All stocks info fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching and saving all stocks info.");
        }
    }

    @GetMapping("/weekly/latest")
    public ResponseEntity<List<StockWithDataResponseDto>> getLatestWeeklyStocksData() {
        try {
            List<StockWithDataResponseDto> responseList = stockService.getLatestWeeklyStocksData();
            if (responseList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/fetch-by-code-and-date")
    public ResponseEntity<? super FetchStockDataResponseDto> fetchStockDataByCodeAndDate(
            @RequestBody StockDataRequestDto requestBody
    ) {
        try {
            return stockService.fetchStockDataByCodeAndDate(requestBody);
        } catch (Exception e) {
            return FetchStockDataResponseDto.databaseError();
        }
    }
}
