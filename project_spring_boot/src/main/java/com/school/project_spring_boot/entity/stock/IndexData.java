package com.school.project_spring_boot.entity.stock;

import jakarta.persistence.*;

@Entity
public class IndexData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "index_data_seq")
    @SequenceGenerator(name = "index_data_seq", sequenceName = "index_data_seq", allocationSize = 1)
    private Long id;


    @Column(nullable = false)
    private String basDt; // 기준일자

    @Column(nullable = false)
    private String idxNm; // 지수명

    @Column(nullable = false)
    private String clpr; // 종가

    @Column(nullable = false)
    private String fltRt; // 등락률

    public IndexData() {
    }

    public IndexData(Long id, String basDt, String idxNm, String clpr, String fltRt) {
        this.id = id;
        this.basDt = basDt;
        this.idxNm = idxNm;
        this.clpr = clpr;
        this.fltRt = fltRt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBasDt() {
        return basDt;
    }

    public void setBasDt(String basDt) {
        this.basDt = basDt;
    }

    public String getIdxNm() {
        return idxNm;
    }

    public void setIdxNm(String idxNm) {
        this.idxNm = idxNm;
    }

    public String getClpr() {
        return clpr;
    }

    public void setClpr(String clpr) {
        this.clpr = clpr;
    }

    public String getFltRt() {
        return fltRt;
    }

    public void setFltRt(String fltRt) {
        this.fltRt = fltRt;
    }
}
