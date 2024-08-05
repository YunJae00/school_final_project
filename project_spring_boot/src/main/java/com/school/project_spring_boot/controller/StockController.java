package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.requset.stock.StockDataRequestDto;
import com.school.project_spring_boot.dto.response.stock.FetchStockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockDataResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockResponseDto;
import com.school.project_spring_boot.dto.response.stock.StockWithDataResponseDto;
import com.school.project_spring_boot.entity.stock.Stock;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendation;
import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendationStock;
import com.school.project_spring_boot.service.stock.DailyStockDataService;
import com.school.project_spring_boot.service.stock.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    // 주식 검색
    @GetMapping("/search")
    public ResponseEntity<List<StockResponseDto>> searchStocks(@RequestParam String query) {
        List<StockResponseDto> stocks = stockService.searchStocks(query);
        return ResponseEntity.ok(stocks);
    }

    // 주차별 주식 리스트 불러오기
    @GetMapping("/weekly")
    public ResponseEntity<List<StockResponseDto>> getWeeklyStocks(@RequestParam String startDate) {
        LocalDate start = LocalDate.parse(startDate);
        List<StockResponseDto> weeklyStocks = stockService.getWeeklyStocks(start);
        return ResponseEntity.ok(weeklyStocks);
    }

    // 주차별 주식 리스트에 추가
    @PostMapping("/weekly/{stockId}")
    public ResponseEntity<Void> addStockToWeekly(@RequestParam String startDate, @PathVariable Long stockId) {
        LocalDate start = LocalDate.parse(startDate);
        stockService.addStockToWeekly(start, stockId);
        return ResponseEntity.ok().build();
    }

    // 주차별 주식 리스트에서 삭제
    @DeleteMapping("/weekly/{stockId}")
    public ResponseEntity<Void> removeStockFromWeekly(@RequestParam String startDate, @PathVariable Long stockId) {
        LocalDate start = LocalDate.parse(startDate);
        stockService.removeStockFromWeekly(start, stockId);
        return ResponseEntity.ok().build();
    }

    // 주차별 주식 데이터 저장
    @PostMapping("/weekly/save")
    public ResponseEntity<String> saveWeeklyStockData(@RequestParam String startDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            stockService.saveWeeklyStockData(start);
            return ResponseEntity.ok("주간 주식 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("주간 주식 데이터를 저장하는 중 오류가 발생했습니다.");
        }
    }

    // 주식 10종 데이터 전체 저장
    @PostMapping("/fetch-multiple-stocks")
    public ResponseEntity<String> fetchMultipleStockDataByCodeAndDate(@RequestBody List<StockDataRequestDto> stockRequests) {
        try {
            for (StockDataRequestDto request : stockRequests) {
                ResponseEntity<? super FetchStockDataResponseDto> response = dailyStockDataService.fetchAndSaveStockDataByCodeAndDate(request);

                if (!response.getStatusCode().is2xxSuccessful()) {
                    return ResponseEntity.status(response.getStatusCode()).body("Error occurred while fetching data for ISIN: " + request.getIsinCd());
                }
            }
            return ResponseEntity.ok("All stock data successfully downloaded and saved.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while downloading stock data.");
        }
    }

    // stock 기본 정보 table 채우기
    @PostMapping("/fetch-all-stocks-info")
    public ResponseEntity<String> fetchAndSaveAllStocksInfo() {
        try {
            stockService.fetchAndSaveAllStocksInfo();
            return ResponseEntity.ok("All stocks info fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while fetching and saving all stocks info.");
        }
    }

    ///////
    // 위에가 admin용
    ///////

    @GetMapping("/weekly/latest")
    public ResponseEntity<List<StockWithDataResponseDto>> getLatestWeeklyStocksData() {
        try {
            WeeklyStockRecommendation latestWeeklyStocks = stockService.getLatestWeeklyStocks();
            if (latestWeeklyStocks == null) {
                return ResponseEntity.noContent().build(); // 최근 주차 데이터가 없을 경우
            }

            List<StockWithDataResponseDto> responseList = new ArrayList<>();

            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusYears(1);

            for (WeeklyStockRecommendationStock stockRec : latestWeeklyStocks.getStocks()) {
                Stock stock = stockRec.getStock();
                List<StockDataResponseDto> stockData = stockService.getStockDataByDateRange(stock.getIsinCd(), startDate, endDate);

                StockWithDataResponseDto stockWithData = new StockWithDataResponseDto(
                        stock.getIsinCd(),
                        stock.getItmsNm(),
                        stockData
                );
                responseList.add(stockWithData);
            }

            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 서버 오류 발생 시
        }
    }


    // postman

    // 선정 데이터 1년치 데이터 다운로드
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
}
