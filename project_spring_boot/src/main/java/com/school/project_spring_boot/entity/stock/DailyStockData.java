package com.school.project_spring_boot.entity.stock;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class DailyStockData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    @JsonBackReference
    private Stock stock;

    @Column(nullable = false)
    private LocalDate basDt; // 기준일자

    @Column(nullable = false)
    private BigDecimal clpr; // 종가

    @Column(nullable = false)
    private BigDecimal hipr; // 고가

    @Column(nullable = false)
    private BigDecimal lopr; // 저가

    @Column(nullable = false)
    private BigDecimal mkp; // 시가

    @Column(nullable = false)
    private BigDecimal vs; // 대비

    @Column(nullable = false)
    private BigDecimal fltRt; // 등락률

    @Column(nullable = false)
    private Long trqu; // 거래량

    @Column(nullable = false)
    private BigDecimal trPrc; // 거래대금

    @Column(nullable = false)
    private Long lstgStCnt; // 상장주식수

    @Column(nullable = false)
    private Long mrktTotAmt; // 시가총액
}
