package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.DailyStockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockDataRepository extends JpaRepository<DailyStockData, Long> {
}

