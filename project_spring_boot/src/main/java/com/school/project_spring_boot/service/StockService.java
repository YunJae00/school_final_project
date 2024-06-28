package com.school.project_spring_boot.service;

import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void saveStockData(String isinCd, String srtnCd, String itmsNm, String mrktCls, List<DailyStockData> dailyStockDataList) {
        Optional<Stock> optionalStock = stockRepository.findByIsinCd(isinCd);
        Stock stock;
        if (optionalStock.isPresent()) {
            stock = optionalStock.get();
        } else {
            stock = new Stock();
            stock.setIsinCd(isinCd);
            stock.setSrtnCd(srtnCd);
            stock.setItmsNm(itmsNm);
            stock.setMrktCls(mrktCls);
        }

        for (DailyStockData dailyStockData : dailyStockDataList) {
            dailyStockData.setStock(stock);
            stock.getDailyStockData().add(dailyStockData);
        }

        stockRepository.save(stock);
    }
}
