package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.stock.WeeklyStockRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeeklyStockRecommendationRepository extends JpaRepository<WeeklyStockRecommendation, Integer> {
    Optional<WeeklyStockRecommendation> findByStartDate(LocalDate startDate);
}
