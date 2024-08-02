package com.school.project_spring_boot.entity.stock;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    public Stock() {
    }

    public Stock(Long id, String isinCd, String srtnCd, String itmsNm, String mrktCls, List<DailyStockData> dailyStockData) {
        this.id = id;
        this.isinCd = isinCd;
        this.srtnCd = srtnCd;
        this.itmsNm = itmsNm;
        this.mrktCls = mrktCls;
        this.dailyStockData = dailyStockData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsinCd() {
        return isinCd;
    }

    public void setIsinCd(String isinCd) {
        this.isinCd = isinCd;
    }

    public String getSrtnCd() {
        return srtnCd;
    }

    public void setSrtnCd(String srtnCd) {
        this.srtnCd = srtnCd;
    }

    public String getItmsNm() {
        return itmsNm;
    }

    public void setItmsNm(String itmsNm) {
        this.itmsNm = itmsNm;
    }

    public String getMrktCls() {
        return mrktCls;
    }

    public void setMrktCls(String mrktCls) {
        this.mrktCls = mrktCls;
    }

    public List<DailyStockData> getDailyStockData() {
        return dailyStockData;
    }

    public void setDailyStockData(List<DailyStockData> dailyStockData) {
        this.dailyStockData = dailyStockData;
    }
}
