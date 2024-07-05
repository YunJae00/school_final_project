package com.school.project_spring_boot.service;

import com.school.project_spring_boot.dto.StockDto;
import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.repository.DailyStockDataRepository;
import com.school.project_spring_boot.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final DailyStockDataRepository dailyStockDataRepository;

    public StockService(StockRepository stockRepository, DailyStockDataRepository dailyStockDataRepository) {
        this.stockRepository = stockRepository;
        this.dailyStockDataRepository = dailyStockDataRepository;
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

    public List<StockDto> getStockData(String isinCd) {
        Optional<Stock> stockOpt = stockRepository.findByIsinCd(isinCd);
        if (stockOpt.isPresent()) {
            Stock stock = stockOpt.get();
            List<DailyStockData> dailyStockDataList = dailyStockDataRepository.findByStock(stock);
            return dailyStockDataList.stream()
                    .map(data -> new StockDto(
                            data.getBasDt(),
                            data.getMkp(),
                            data.getHipr(),
                            data.getLopr(),
                            data.getClpr()))
                    .collect(Collectors.toList());
        }
        else return null;
    }
}
