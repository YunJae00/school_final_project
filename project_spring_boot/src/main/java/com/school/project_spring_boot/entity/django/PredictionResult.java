package com.school.project_spring_boot.entity.django;

import com.school.project_spring_boot.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PredictionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private String action;  // 예측된 행동: Buy, Sell, Hold
    private String targetDate;  // 예측 대상 날짜
}
