package com.school.project_spring_boot.dto.response.stock;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class StockDataResponseDto {
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

    public StockDataResponseDto() {
    }

    public StockDataResponseDto(LocalDate basDt, BigDecimal clpr, BigDecimal hipr, BigDecimal lopr, BigDecimal mkp, BigDecimal vs, BigDecimal fltRt, Long trqu, BigDecimal trPrc, Long lstgStCnt, Long mrktTotAmt) {
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
}
