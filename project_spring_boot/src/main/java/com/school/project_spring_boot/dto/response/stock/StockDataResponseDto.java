package com.school.project_spring_boot.dto.response.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
