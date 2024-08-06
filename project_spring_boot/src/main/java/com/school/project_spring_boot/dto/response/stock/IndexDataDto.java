package com.school.project_spring_boot.dto.response.stock;

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

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFluctuation() {
        return fluctuation;
    }

    public void setFluctuation(String fluctuation) {
        this.fluctuation = fluctuation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
