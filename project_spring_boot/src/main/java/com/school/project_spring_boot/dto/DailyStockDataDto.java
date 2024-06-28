package com.school.project_spring_boot.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailyStockDataDto {
    private LocalDate basDt;
    private BigDecimal clpr;
    private BigDecimal hipr;
    private BigDecimal lopr;
    private BigDecimal mkp;
    private BigDecimal vs;
    private BigDecimal fltRt;
    private Long trqu;
    private BigDecimal trPrc;
    private Long lstgStCnt;
    private Long mrktTotAmt;

    public DailyStockDataDto() {
    }

    public DailyStockDataDto(LocalDate basDt, BigDecimal clpr, BigDecimal hipr, BigDecimal lopr, BigDecimal mkp, BigDecimal vs, BigDecimal fltRt, Long trqu, BigDecimal trPrc, Long lstgStCnt, Long mrktTotAmt) {
        this.basDt = basDt;
        this.clpr = clpr;
        this.hipr = hipr;
        this.lopr = lopr;
        this.mkp = mkp;
        this.vs = vs;
        this.fltRt = fltRt;
        this.trqu = trqu;
        this.trPrc = trPrc;
        this.lstgStCnt = lstgStCnt;
        this.mrktTotAmt = mrktTotAmt;
    }

    public LocalDate getBasDt() {
        return basDt;
    }

    public void setBasDt(LocalDate basDt) {
        this.basDt = basDt;
    }

    public BigDecimal getClpr() {
        return clpr;
    }

    public void setClpr(BigDecimal clpr) {
        this.clpr = clpr;
    }

    public BigDecimal getHipr() {
        return hipr;
    }

    public void setHipr(BigDecimal hipr) {
        this.hipr = hipr;
    }

    public BigDecimal getLopr() {
        return lopr;
    }

    public void setLopr(BigDecimal lopr) {
        this.lopr = lopr;
    }

    public BigDecimal getMkp() {
        return mkp;
    }

    public void setMkp(BigDecimal mkp) {
        this.mkp = mkp;
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
