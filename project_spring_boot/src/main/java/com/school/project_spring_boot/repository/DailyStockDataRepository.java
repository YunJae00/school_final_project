package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.DailyStockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyStockDataRepository extends JpaRepository<DailyStockData, Long> {
    List<DailyStockData> findByStockIsinCdAndBasDtBetween(String isinCd, LocalDate startDate, LocalDate endDate);
}
