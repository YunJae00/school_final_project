package com.school.project_spring_boot.dto.response.stock;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockWithDataResponseDto {
    private String isinCd;
    private String itmsNm;
    private List<StockDataResponseDto> stockData;

    public StockWithDataResponseDto(String isinCd, String itmsNm, List<StockDataResponseDto> stockData) {
        this.isinCd = isinCd;
        this.itmsNm = itmsNm;
        this.stockData = stockData;
    }
}
