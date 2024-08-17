package com.school.project_spring_boot.service.scheduler;

import com.school.project_spring_boot.service.django.DjangoService;
import com.school.project_spring_boot.service.stock.IndexService;
import com.school.project_spring_boot.service.stock.StockService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;

@Service
public class SchedulerService {

    DjangoService djangoService;
    StockService stockService;
    IndexService indexService;

    public SchedulerService(DjangoService djangoService, StockService stockService, IndexService indexService) {
        this.djangoService = djangoService;
        this.stockService = stockService;
        this.indexService = indexService;
    }

    // 매달 한번 실행 (모델 갱신 1달에 한번 KOSPI200 기준으로 실행)
    @Scheduled(cron = "0 0 0 1 * *")  // 매월 1일 00:00에 실행
    public void scheduleTrainModel() {
        djangoService.startTraining("KS200", "2023-01-01", 50, 10);
    }

    // 매주 한번 실행 (주식 모델 테스트 1주에 한번 실행)
    @Scheduled(cron = "0 0 0 * * SUN")  // 매주 일요일 00:00에 실행
    public void scheduleTestAndSaveWeeklyStocks() {
        djangoService.testAndSaveWeeklyStocks();
    }

    // 매일 한번 실행 (주식 예측 매일 6시 실행)
    @Scheduled(cron = "0 0 6 * * *")  // 매일 06:00에 실행
    public void schedulePredictAndSaveWeeklyStocks() {
        djangoService.predictAndSaveWeeklyStocks();
    }

    // 2주에 한번 실행 (주식 리스트 데이터 갱신)
    @Scheduled(cron = "0 0 0 1,15 * *")  // 매월 1일, 15일 00:00에 실행
    public void scheduleFetchAndSaveAllStocksInfo() {
        stockService.fetchAndSaveAllStocksInfo();
    }

    // 매일 한번 실행 (주가 지수 데이터 매일 갱신
    @Scheduled(cron = "0 0 6 * * *")  // 매일 06:00에 실행
    public void scheduleSaveLatestIndexData() {
        List<String> indexNames = Arrays.asList("코스피", "코스닥", "코스피 200", "코스닥 150", "KRX 100", "코스피 50");
        for (String indexName : indexNames) {
            indexService.fetchAndSaveLatestIndexData(indexName);
        }
    }
}
