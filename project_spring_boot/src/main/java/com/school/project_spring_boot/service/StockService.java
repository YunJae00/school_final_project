package com.school.project_spring_boot.service;

import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.exceptionHandler.StockNotFoundException;
import com.school.project_spring_boot.repository.DailyStockDataRepository;
import com.school.project_spring_boot.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final DailyStockDataRepository dailyStockDataRepository;

    public StockService(StockRepository stockRepository, DailyStockDataRepository dailyStockDataRepository) {
        this.stockRepository = stockRepository;
        this.dailyStockDataRepository = dailyStockDataRepository;
    }

    public void saveStockData(String isinCd, String srtnCd, String itmsNm, String mrktCls, List<DailyStockData> dailyStockDataList) {
        Stock stock = stockRepository.findByIsinCd(isinCd).orElseGet(() -> {
            Stock newStock = new Stock();
            newStock.setIsinCd(isinCd);
            newStock.setSrtnCd(srtnCd);
            newStock.setItmsNm(itmsNm);
            newStock.setMrktCls(mrktCls);
            return newStock;
        });
        stockRepository.save(stock);

        for (DailyStockData data : dailyStockDataList) {
            data.setStock(stock);
            dailyStockDataRepository.save(data);
        }
    }

    public List<DailyStockData> getDailyStockData(String isinCd, LocalDate startDate, LocalDate endDate) {
        Stock stock = stockRepository.findByIsinCd(isinCd)
                .orElseThrow(() -> new StockNotFoundException("Stock not found for ISIN code: " + isinCd));
        return dailyStockDataRepository.findByStockAndBasDtBetween(stock, startDate, endDate);
    }
}
