package com.school.project_spring_boot.entity.stock;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IndexData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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
}
