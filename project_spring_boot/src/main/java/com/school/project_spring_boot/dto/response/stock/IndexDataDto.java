package com.school.project_spring_boot.dto.response.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexDataDto {
    private String indexName;
    private String price;
    private String fluctuation;
    private String date;
}
