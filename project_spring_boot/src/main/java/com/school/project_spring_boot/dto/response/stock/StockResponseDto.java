package com.school.project_spring_boot.dto.response.stock;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StockResponseDto {

    private Long id;
    private String itmsNm; // 주식 이름
    private String isinCd; // 주식 코드

    // 기본 생성자
    public StockResponseDto() {
    }

    // 모든 필드를 포함하는 생성자
    public StockResponseDto(Long id, String itmsNm, String isinCd) {
        this.id = id;
        this.itmsNm = itmsNm;
        this.isinCd = isinCd;
    }
}
