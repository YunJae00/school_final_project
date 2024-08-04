package com.school.project_spring_boot.entity.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WeeklyStockRecommendationStock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "weekly_stock_recommendation_id")
    private WeeklyStockRecommendation weeklyStockRecommendation;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
