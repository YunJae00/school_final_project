package com.school.project_spring_boot.dto.response.stock;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseDto {
    private Long id;
    private String itmsNm; // 주식 이름
    private String isinCd; // 주식 코드
}
