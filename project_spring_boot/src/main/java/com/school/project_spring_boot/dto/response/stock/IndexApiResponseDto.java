package com.school.project_spring_boot.dto.response.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexApiResponseDto {

    @JsonProperty("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty("body")
        private Body body;

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {
        @JsonProperty("items")
        private Items items;

        public Items getItems() {
            return items;
        }

        public void setItems(Items items) {
            this.items = items;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Items {
        @JsonProperty("item")
        private List<Item> item;

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        @JsonProperty("basDt")
        private String basDt;

        @JsonProperty("idxNm")
        private String idxNm;

        @JsonProperty("clpr")
        private String clpr;

        @JsonProperty("fltRt")
        private String fltRt;

        // 필요한 다른 필드 추가

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
}
