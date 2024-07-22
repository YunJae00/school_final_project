import React from "react";
import styled from "styled-components";

const StockBoxWrapper = styled.div`
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: space-between;
    align-items: start;
    height: 33.125rem;
    border-radius: 2.25rem;
    padding: 2.5rem;
    background-color: white;
`;

const StockNumber = styled.p`
    font-size: 1rem;
    font-family: pretendard;
`;

const StockTitle = styled.p`
    font-size: 2rem;
    font-weight: bold;
    font-family: pretendard;
`;

const StockCode = styled.p`
    font-size: 1.25rem;
    font-family: pretendard;
`;

const StockGraph = styled.div`
    height: 15.625rem;
`;

const StockDetailBox = styled.div`
    display: flex;
    width: 100%;
    justify-content: space-between;
    align-items: center;
`;

const StockPrice = styled.p`
    font-size: 1.25rem;
    font-family: pretendard;
`;

const StockDate = styled.p`
    font-size: 1.25rem;
    font-family: pretendard;
`;

const StockBox = ({stockNumber, stockTitle, stockCode, stockGraph, stockPrice, stockDate}) => {
    return(
        <StockBoxWrapper>
            <StockNumber>{stockNumber}</StockNumber>
            <StockTitle>{stockTitle}</StockTitle>
            <StockCode>{stockCode}</StockCode>
            <StockGraph></StockGraph>
            <StockDetailBox>
                <StockPrice>현재가</StockPrice>
                <StockPrice>{stockPrice}</StockPrice>
            </StockDetailBox>
            <StockDetailBox>
                <StockDate>기준일</StockDate>
                <StockDate>{stockDate}</StockDate>
            </StockDetailBox>
        </StockBoxWrapper>
    );
};

export default StockBox;