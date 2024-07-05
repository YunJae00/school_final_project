import React from "react";
import styled from "styled-components";
import searchIcon from "../../../../images/search_icon.png"
import StockDetailListComponent from "./components/StockDetailListComponent";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    background-color: #486284;
    padding: 20px;
    gap: 18px;
    border-radius: 10px;
`;

const DetailTitleContainer = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-radius: 5px;
`;

const DetailTitle = styled.p`
    font-weight: bold;
    color: white;
    font-size: 17px;
`;

const SearchContainer = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 5px 10px;
    border-radius: 20px;
    background-color: white;
    gap: 10px;
`;

const SearchInput = styled.input`
    font-size: 16px;
    outline: none;
    border: none;
    padding: 5px 10px;
`;

const ColumnNamesContainer = styled.div`
    display: flex;
    justify-content: space-between;
    color: white;
    font-weight: bold;
    padding: 10px 0;
    border-bottom: 1px solid white;
`;

const ColumnContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 30px;
`;

const ColumnName = styled.div`
    color: white;
    width: 70px;
    font-size: 16px;
    opacity: 0.9;
`;

const StockDetailContainer = () => {
    return (
        <Container>
            <DetailTitleContainer>
                <DetailTitle>한국 주식 ></DetailTitle>
                <SearchContainer>
                    <img src={searchIcon} alt={"searchIcon"} style={{width: "20px", height: "20px", opacity: "0.5"}}/>
                    <SearchInput type="text" placeholder="Search.." />
                </SearchContainer>
            </DetailTitleContainer>
            <ColumnNamesContainer>
                <p style={{width: "100px", color: "white", fontSize: "15px", fontWeight: "bold"}}>종목명</p>
                <ColumnContainer>
                    <ColumnName>종가</ColumnName>
                    <ColumnName>고가</ColumnName>
                    <ColumnName>저가</ColumnName>
                    <ColumnName>변동</ColumnName>
                    <ColumnName style={{width: "80px"}}>변동%</ColumnName>
                    <ColumnName style={{width: "100px"}}>거래량</ColumnName>
                </ColumnContainer>
            </ColumnNamesContainer>
            <StockDetailListComponent
                name="삼성전자"
                price="70,000"
                high="71,000"
                low="69,000"
                change="+1,000"
                changePercent="+1.43%"
                volume="15,000,000"
            />
            <StockDetailListComponent
                name="LG전자"
                price="150,000"
                high="155,000"
                low="149,000"
                change="-500"
                changePercent="-0.33%"
                volume="8,000,000"
            />
            <StockDetailListComponent
                name="SK하이닉스"
                price="120,000"
                high="122,000"
                low="118,000"
                change="+2,000"
                changePercent="+1.69%"
                volume="10,000,000"
            />
            <StockDetailListComponent
            name="삼성전자"
            price="70,000"
            high="71,000"
            low="69,000"
            change="+1,000"
            changePercent="+1.43%"
            volume="15,000,000"
            />
            <StockDetailListComponent
                name="LG전자"
                price="150,000"
                high="155,000"
                low="149,000"
                change="-500"
                changePercent="-0.33%"
                volume="8,000,000"
            />
            <StockDetailListComponent
                name="SK하이닉스"
                price="120,000"
                high="122,000"
                low="118,000"
                change="+2,000"
                changePercent="+1.69%"
                volume="10,000,000"
            />
        </Container>
    );
}

export default StockDetailContainer;
