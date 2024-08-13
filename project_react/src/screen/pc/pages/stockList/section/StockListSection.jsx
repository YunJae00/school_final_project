import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate 사용
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import StockBox from "../../components/StockBox";
import { executeGetWeeklyStocksForList } from "../../../../../api/ApiService";
import {useDate} from "../../../../../context/date/DateContext";

const StockDetailSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #99B5F9;
`;

const StockDetailSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const StockDetailBoxContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    justify-content: center;
    gap: 1.875rem;
`;

const StockDetailBoxRow = styled.div`
    display: flex;
    width: 100%;
    gap: 1.875rem;
`;

const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("ko-KR", { month: "long", day: "numeric" });
}

const getYear = (dateString) => {
    const date = new Date(dateString);
    return `${date.getFullYear()}년`;
};

const StockListSection = () => {
    const { startDate, endDate } = useDate();

    const [stocks, setStocks] = useState([]); // 주식 데이터를 저장할 상태 변수
    const navigate = useNavigate(); // useNavigate 훅을 사용

    useEffect(() => {
        // API 호출
        executeGetWeeklyStocksForList() // 필요에 따라 날짜를 수정하세요.
            .then(response => {
                setStocks(response.data); // 모든 데이터를 설정
            })
            .catch(error => {
                console.error('Error fetching weekly stocks:', error);
            });
    }, []);

    const handleStockClick = (stock) => {
        navigate(`/stock-detail/${stock.isinCd}`, { state: { stock } }); // 주식 데이터와 함께 이동
    };

    return (
        <StockDetailSectionWrapper>
            <StockDetailSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"} />
                <StockDetailBoxContainer>
                    <StockDetailBoxRow>
                        <div style={{ display: "flex", flex: "1", gap: "1.875rem" }}>
                            <IntroductionBox
                                subTitle={"AI 모델 성과"}
                                title={`${getYear(startDate)}\n${formatDate(startDate)} ~ ${formatDate(endDate)}\nTrand Trader 선정 주식 종목`}
                                content={"1. 서비스에서 선정된 자산별 현황을 확인해보세요\n"
                                    + "2. 선정된 종목을 확인하고 개인 포트폴리오에 추가해보세요\n"
                                    + "3. 선정된 종목의 AI 분석을 확인해보세요"}
                                detail={"⦁ 종목은 매 주 시가총액 상위 10개 종목으로 선정됩니다."} />
                        </div>
                        <div style={{ display: "flex", flex: "1", gap: "1.875rem" }}>
                            {stocks.slice(0, 2).map((stock, index) => (
                                <StockBox
                                    key={index}
                                    stockNumber={index + 1}
                                    stockTitle={stock.itmsNm}
                                    stockCode={stock.isinCd}
                                    stockData={stock.stockData}
                                    onClick={() => handleStockClick(stock)} // 클릭 이벤트 추가
                                />
                            ))}
                        </div>
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        {stocks.slice(2, 6).map((stock, index) => (
                            <StockBox
                                key={index + 2}
                                stockNumber={index + 3}
                                stockTitle={stock.itmsNm}
                                stockCode={stock.isinCd}
                                stockData={stock.stockData}
                                onClick={() => handleStockClick(stock)} // 클릭 이벤트 추가
                            />
                        ))}
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        {stocks.slice(6, 10).map((stock, index) => (
                            <StockBox
                                key={index + 6}
                                stockNumber={index + 7}
                                stockTitle={stock.itmsNm}
                                stockCode={stock.isinCd}
                                stockData={stock.stockData}
                                onClick={() => handleStockClick(stock)} // 클릭 이벤트 추가
                            />
                        ))}
                    </StockDetailBoxRow>
                </StockDetailBoxContainer>
            </StockDetailSectionContainer>
        </StockDetailSectionWrapper>
    );
};

export default StockListSection;
