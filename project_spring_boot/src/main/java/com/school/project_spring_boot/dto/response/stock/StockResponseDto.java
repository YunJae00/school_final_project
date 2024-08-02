package com.school.project_spring_boot.dto.response.stock;

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

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItmsNm() {
        return itmsNm;
    }

    public void setItmsNm(String itmsNm) {
        this.itmsNm = itmsNm;
    }

    public String getIsinCd() {
        return isinCd;
    }

    public void setIsinCd(String isinCd) {
        this.isinCd = isinCd;
    }
}
