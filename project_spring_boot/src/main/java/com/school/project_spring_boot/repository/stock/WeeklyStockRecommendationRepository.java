package com.school.project_spring_boot.repository.stock;

import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeeklyStockRecommendationRepository extends JpaRepository<WeeklyStockRecommendation, Integer> {
    // 특정 시작 날짜로 주간 추천 주식을 찾기 위한 메서드
    Optional<WeeklyStockRecommendation> findByStartDate(LocalDate startDate);

    // 가장 최근 주간 추천 주식을 찾기 위한 메서드
    Optional<WeeklyStockRecommendation> findTopByOrderByStartDateDesc();

}
