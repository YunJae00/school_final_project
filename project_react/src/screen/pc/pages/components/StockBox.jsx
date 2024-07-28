import React, { useEffect, useState } from "react";
import styled from "styled-components";
import StockGraph from './StockGraph';
import {executeGetDailyStockData} from "../../../../api/ApiService";

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

const StockBox = ({ stockNumber, stockTitle, stockCode, startDate, endDate }) => {
    const [latestPrice, setLatestPrice] = useState(null);
    const [latestDate, setLatestDate] = useState(null);
    const [data, setData] = useState([]);

    useEffect(() => {
        if (stockCode) {
            executeGetDailyStockData(stockCode, startDate, endDate)
                .then(response => {
                    setData(response.data);
                    if (response.data.length > 0) {
                        const latestData = response.data[0];
                        setLatestPrice(latestData.clpr);
                        setLatestDate(latestData.basDt);
                    }
                })
                .catch(error => {
                    console.error('Error fetching stock data:', error);
                });
        }
    }, [stockCode, startDate, endDate]);

    return (
        <StockBoxWrapper>
            <StockNumber>{stockNumber}</StockNumber>
            <StockTitle>{stockTitle}</StockTitle>
            <StockCode>{stockCode}</StockCode>
            <StockGraphWrapper>
                <StockGraph stockCode={stockCode} startDate={startDate} endDate={endDate} />
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
