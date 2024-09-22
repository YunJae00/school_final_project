import React, { useEffect, useState } from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import StockBox from "../../components/StockBox";
import { useNavigate } from "react-router-dom";
import { executeGetWeeklyStocksForList } from "../../../../../api/ApiService";
import {useDate} from "../../../../../context/date/DateContext";

const StockSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #99B5F9;
`;

const StockSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const StockBoxContainer = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    gap: 1.875rem;
`;

const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("ko-KR", { month: "long", day: "numeric" });
};

const getYear = (dateString) => {
    const date = new Date(dateString);
    return `${date.getFullYear()}년`; // 2024년이라면 "2024년"으로 변환
};

const StockSection = () => {
    const { startDate, endDate} = useDate();

    const navigate = useNavigate();
    const [stocks, setStocks] = useState([]); // 주식 데이터를 저장할 상태 변수

    useEffect(() => {
        // API 호출
        executeGetWeeklyStocksForList() // 필요에 따라 날짜를 수정하세요.
            .then(response => {
                setStocks(response.data.slice(0, 3)); // API에서 받은 데이터 중 3개만 상태로 설정
            })
            .catch(error => {
                console.error('Error fetching weekly stocks:', error);
            });
    }, []);

    function buttonClickHandler() {
        navigate('/stock-list'); // 버튼 클릭 시 stock-list 페이지로 이동
    }

    const handleStockClick = (stock) => {
        navigate(`/stock-detail/${stock.isinCd}`, { state: { stock } }); // 주식 데이터와 함께 상세 페이지로 이동
    };

    return (
        <StockSectionWrapper>
            <StockSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"} />
                <StockBoxContainer>
                    <IntroductionBox
                        onClick={buttonClickHandler}
                        subTitle={"AI 모델 성과"}
                        title={`${getYear(startDate)}\n${formatDate(startDate)} ~ ${formatDate(endDate)}\nSTOPICKR\n선정 주식 종목`}
                        content={"서비스에서 선정된\n자산별 현황을 확인해보세요"}
                        detail={"⦁ 종목은 매 주 시가총액 상위 10개 종목으로 선정됩니다."}
                        buttonText={"10 종목 모두 확인하러가기"} />
                    {stocks.map((stock, index) => (
                        <StockBox
                            key={index}
                            stockNumber={index + 1}
                            stockTitle={stock.itmsNm}
                            stockCode={stock.isinCd}
                            stockData={stock.stockData}
                            onClick={() => handleStockClick(stock)} // 각 StockBox에 클릭 이벤트 추가
                        />
                    ))}
                </StockBoxContainer>
            </StockSectionContainer>
        </StockSectionWrapper>
    );
};

export default StockSection;
