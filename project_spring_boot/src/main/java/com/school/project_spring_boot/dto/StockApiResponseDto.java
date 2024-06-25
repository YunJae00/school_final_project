package com.school.project_spring_boot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockApiResponseDto {
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
        @JsonProperty("header")
        private Header header;

        @JsonProperty("body")
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;

        @JsonProperty("resultMsg")
        private String resultMsg;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultMsg() {
            return resultMsg;
        }

        public void setResultMsg(String resultMsg) {
            this.resultMsg = resultMsg;
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

        @JsonProperty("clpr")
        private String clpr;

        @JsonProperty("hipr")
        private String hipr;

        @JsonProperty("lopr")
        private String lopr;

        @JsonProperty("mkp")
        private String mkp;

        @JsonProperty("vs")
        private String vs;

        @JsonProperty("fltRt")
        private String fltRt;

        @JsonProperty("trqu")
        private String trqu;

        @JsonProperty("trPrc")
        private String trPrc;

        @JsonProperty("lstgStCnt")
        private String lstgStCnt;

        @JsonProperty("mrktTotAmt")
        private String mrktTotAmt;

        @JsonProperty("isinCd")
        private String isinCd;

        @JsonProperty("srtnCd")
        private String srtnCd;

        @JsonProperty("itmsNm")
        private String itmsNm;

        @JsonProperty("mrktCtg")
        private String mrktCtg;

        public String getBasDt() {
            return basDt;
        }

        public void setBasDt(String basDt) {
            this.basDt = basDt;
        }

        public String getClpr() {
            return clpr;
        }

        public void setClpr(String clpr) {
            this.clpr = clpr;
        }

        public String getHipr() {
            return hipr;
        }

        public void setHipr(String hipr) {
            this.hipr = hipr;
        }

        public String getLopr() {
            return lopr;
        }

        public void setLopr(String lopr) {
            this.lopr = lopr;
        }

        public String getMkp() {
            return mkp;
        }

        public void setMkp(String mkp) {
            this.mkp = mkp;
        }

        public String getVs() {
            return vs;
        }

        public void setVs(String vs) {
            this.vs = vs;
        }

        public String getFltRt() {
            return fltRt;
        }

        public void setFltRt(String fltRt) {
            this.fltRt = fltRt;
        }

        public String getTrqu() {
            return trqu;
        }

        public void setTrqu(String trqu) {
            this.trqu = trqu;
        }

        public String getTrPrc() {
            return trPrc;
        }

        public void setTrPrc(String trPrc) {
            this.trPrc = trPrc;
        }

        public String getLstgStCnt() {
            return lstgStCnt;
        }

        public void setLstgStCnt(String lstgStCnt) {
            this.lstgStCnt = lstgStCnt;
        }

        public String getMrktTotAmt() {
            return mrktTotAmt;
        }

        public void setMrktTotAmt(String mrktTotAmt) {
            this.mrktTotAmt = mrktTotAmt;
        }

        public String getIsinCd() {
            return isinCd;
        }

        public void setIsinCd(String isinCd) {
            this.isinCd = isinCd;
        }

        public String getSrtnCd() {
            return srtnCd;
        }

        public void setSrtnCd(String srtnCd) {
            this.srtnCd = srtnCd;
        }

        public String getItmsNm() {
            return itmsNm;
        }

        public void setItmsNm(String itmsNm) {
            this.itmsNm = itmsNm;
        }

        public String getMrktCtg() {
            return mrktCtg;
        }

        public void setMrktCtg(String mrktCtg) {
            this.mrktCtg = mrktCtg;
        }
    }
}
