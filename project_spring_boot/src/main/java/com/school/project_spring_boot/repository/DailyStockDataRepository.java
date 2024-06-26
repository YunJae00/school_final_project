package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyStockDataRepository extends JpaRepository<DailyStockData, Long> {
    @Query("SELECT d FROM DailyStockData d WHERE d.stock = :stock ORDER BY d.basDt DESC")
    List<DailyStockData> findTopByStockOrderByBasDtDesc(@Param("stock") Stock stock);
}

