package com.school.project_spring_boot.service;

import com.school.project_spring_boot.dto.StockDto;
import com.school.project_spring_boot.dto.response.stock.StockDataResponseDto;
import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.repository.DailyStockDataRepository;
import com.school.project_spring_boot.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
            stockRepository.save(stock);
        }

        for (DailyStockData dailyStockData : dailyStockDataList) {
            Optional<DailyStockData> existingData = dailyStockDataRepository.findByStockAndBasDt(stock, dailyStockData.getBasDt());
            if (!existingData.isPresent()) {
                dailyStockData.setStock(stock);
                dailyStockDataRepository.save(dailyStockData);
            }
        }
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
        } else {
            return null;
        }
    }

    public List<StockDataResponseDto> getStockDataByDateRange(String isinCd, LocalDate startDate, LocalDate endDate) {
        Optional<Stock> optionalStock = stockRepository.findByIsinCd(isinCd);
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            return dailyStockDataRepository.findByStockAndBasDtBetween(stock, startDate, endDate).stream()
                    .map(data -> new StockDataResponseDto(
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
        } else {
            throw new RuntimeException("Stock not found");
        }
    }

}