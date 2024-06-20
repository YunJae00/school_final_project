package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyStockDataRepository extends JpaRepository<DailyStockData, Long> {
    List<DailyStockData> findByStockAndBasDtBetween(Stock stock, LocalDate startDate, LocalDate endDate);
}
