package com.school.project_spring_boot.entity.django;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.school.project_spring_boot.entity.stock.Stock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"stock"})
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private double averageProfit;
    private String testRewards;  // JSON 문자열로 저장
    private String testProfits;  // JSON 문자열로 저장
}
