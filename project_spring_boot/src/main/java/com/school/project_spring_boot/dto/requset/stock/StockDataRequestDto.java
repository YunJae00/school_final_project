package com.school.project_spring_boot.dto.requset.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDataRequestDto {
    private String isinCd;
    private String startDate;
    private String endDate;
}
