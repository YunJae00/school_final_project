package com.school.project_spring_boot.entity.stock;

import com.school.project_spring_boot.dto.response.stock.StockResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class WeeklyStockRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "weeklyStockRecommendation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyStockRecommendationStock> stocks = new ArrayList<>();

}
