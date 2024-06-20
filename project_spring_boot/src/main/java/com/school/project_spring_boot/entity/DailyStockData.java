package com.school.project_spring_boot.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class DailyStockData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @Column(nullable = false)
    private LocalDate basDt; // 기준일자

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public LocalDate getBasDt() {
        return basDt;
    }

    public void setBasDt(LocalDate basDt) {
        this.basDt = basDt;
    }

    public BigDecimal getVs() {
        return vs;
    }

    public void setVs(BigDecimal vs) {
        this.vs = vs;
    }

    public BigDecimal getFltRt() {
        return fltRt;
    }

    public void setFltRt(BigDecimal fltRt) {
        this.fltRt = fltRt;
    }

    public Long getTrqu() {
        return trqu;
    }

    public void setTrqu(Long trqu) {
        this.trqu = trqu;
    }

    public BigDecimal getTrPrc() {
        return trPrc;
    }

    public void setTrPrc(BigDecimal trPrc) {
        this.trPrc = trPrc;
    }

    public Long getLstgStCnt() {
        return lstgStCnt;
    }

    public void setLstgStCnt(Long lstgStCnt) {
        this.lstgStCnt = lstgStCnt;
    }

    public Long getMrktTotAmt() {
        return mrktTotAmt;
    }

    public void setMrktTotAmt(Long mrktTotAmt) {
        this.mrktTotAmt = mrktTotAmt;
    }
}
