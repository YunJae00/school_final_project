package com.school.project_spring_boot.dto.requset.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockDataRequestDto {
    private String isinCd;
    private String startDate;
    private String endDate;
}
