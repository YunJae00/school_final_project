package com.school.project_spring_boot.entity.stock;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_seq")
    @SequenceGenerator(name = "stock_seq", sequenceName = "stock_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isinCd; // ISIN 코드

    @Column(unique = true, nullable = false)
    private String srtnCd; // 단축코드

    @Column(nullable = false)
    private String itmsNm; // 종목명

    @Column(nullable = false)
    private String mrktCls = "Unknown"; // 시장 구분

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DailyStockData> dailyStockData = new ArrayList<>();

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeeklyStockRecommendationStock> weeklyStockRecommendations = new ArrayList<>();

    public Stock() {
    }

    public Stock(Long id, String isinCd, String srtnCd, String itmsNm, String mrktCls, List<DailyStockData> dailyStockData, List<WeeklyStockRecommendationStock> weeklyStockRecommendations) {
        this.id = id;
        this.isinCd = isinCd;
        this.srtnCd = srtnCd;
        this.itmsNm = itmsNm;
        this.mrktCls = mrktCls;
        this.dailyStockData = dailyStockData;
        this.weeklyStockRecommendations = weeklyStockRecommendations;
    }
}
