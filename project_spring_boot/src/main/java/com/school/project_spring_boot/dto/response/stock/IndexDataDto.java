package com.school.project_spring_boot.dto.response.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexDataDto {
    private String indexName;
    private String price;
    private String fluctuation;
    private String date;

    public IndexDataDto(String indexName, String price, String fluctuation, String date) {
        this.indexName = indexName;
        this.price = price;
        this.fluctuation = fluctuation;
        this.date = date;
    }
}
