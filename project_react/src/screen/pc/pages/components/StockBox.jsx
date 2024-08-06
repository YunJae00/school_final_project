import React, { useEffect, useState } from "react";
import styled from "styled-components";
import StockGraph from './StockGraph';

const StockBoxWrapper = styled.div`
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: space-between;
    align-items: start;
    height: 28rem;
    border-radius: 2.25rem;
    padding: 2.5rem;
    background-color: white;
    cursor: pointer; // 클릭 가능하도록 커서 스타일 추가
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

const StockGraphWrapper = styled.div`
    height: 15.625rem;
    width: 100%;
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

const StockBox = ({ stockNumber, stockTitle, stockCode, stockData, onClick }) => {
    const [latestPrice, setLatestPrice] = useState(null);
    const [latestDate, setLatestDate] = useState(null);

    useEffect(() => {
        if (stockData && stockData.length > 0) {
            // 최신 날짜 기준으로 데이터 정렬 (가장 최근이 첫 번째로 오도록)
            const sortedData = [...stockData].sort((a, b) => new Date(b.basDt) - new Date(a.basDt));
            const latestData = sortedData[0]; // 가장 최근 날짜의 데이터를 가져옴
            setLatestPrice(latestData.clpr);
            setLatestDate(latestData.basDt);
        }
    }, [stockData]);

    return (
        <StockBoxWrapper onClick={onClick}>
            <StockNumber>{stockNumber}</StockNumber>
            <StockTitle>{stockTitle}</StockTitle>
            <StockCode>{stockCode}</StockCode>
            <StockGraphWrapper>
                <StockGraph stockData={stockData} />
            </StockGraphWrapper>
            <StockDetailBox>
                <StockPrice>현재가</StockPrice>
                <StockPrice>{latestPrice !== null ? latestPrice : 'N/A'}</StockPrice>
            </StockDetailBox>
            <StockDetailBox>
                <StockDate>기준일</StockDate>
                <StockDate>{latestDate !== null ? latestDate : 'N/A'}</StockDate>
            </StockDetailBox>
        </StockBoxWrapper>
    );
};

export default StockBox;
