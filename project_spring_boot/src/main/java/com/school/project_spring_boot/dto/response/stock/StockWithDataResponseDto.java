package com.school.project_spring_boot.dto.response.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockWithDataResponseDto {
    private String isinCd;
    private String itmsNm;
    private List<StockDataResponseDto> stockData;
}
