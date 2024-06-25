package com.school.project_spring_boot.service;

import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.repository.DailyStockDataRepository;
import com.school.project_spring_boot.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final DailyStockDataRepository dailyStockDataRepository;
    private final StockRepository stockRepository;

    public StockService(DailyStockDataRepository dailyStockDataRepository, StockRepository stockRepository) {
        this.dailyStockDataRepository = dailyStockDataRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void saveStockData(String isinCd, String srtnCd, String itmsNm, String mrktCls, List<DailyStockData> dailyStockDataList) {
        Optional<Stock> stockOptional = stockRepository.findByIsinCd(isinCd);
        Stock stock;
        if (stockOptional.isPresent()) {
            stock = stockOptional.get();
        } else {
            stock = new Stock();
            stock.setIsinCd(isinCd);
            stock.setSrtnCd(srtnCd);
            stock.setItmsNm(itmsNm);
            stock.setMrktCls(mrktCls);
            stock = stockRepository.save(stock);
        }

        for (DailyStockData dailyStockData : dailyStockDataList) {
            dailyStockData.setStock(stock);
            dailyStockDataRepository.save(dailyStockData);
        }
    }

    public List<DailyStockData> getStockData(String isinCd, LocalDate startDate, LocalDate endDate) {
        return dailyStockDataRepository.findByStockIsinCdAndBasDtBetween(isinCd, startDate, endDate);
    }
}
