package com.school.project_spring_boot.repository.stock;

import com.school.project_spring_boot.entity.stock.DailyStockData;
import com.school.project_spring_boot.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyStockDataRepository extends JpaRepository<DailyStockData, Long> {

    @Query("SELECT d FROM DailyStockData d WHERE d.stock = :stock ORDER BY d.basDt DESC")
    List<DailyStockData> findTopByStockOrderByBasDtDesc(@Param("stock") Stock stock);

    List<DailyStockData> findByStock(Stock stock);

    Optional<DailyStockData> findByStockAndBasDt(Stock stock, LocalDate basDt);

    List<DailyStockData> findByStockAndBasDtBetween(Stock stock, LocalDate startDate, LocalDate endDate);

    // 특정 주식의 특정 날짜 데이터가 존재하는지 확인하는 메서드
    boolean existsByStockAndBasDt(Stock stock, LocalDate basDt);
}

