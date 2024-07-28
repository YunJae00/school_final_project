import React, {useState} from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import StockBox from "../../components/StockBox";
import {useNavigate} from "react-router-dom";

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

const StockSection = () => {

    const navigate = useNavigate();

    const [startDate, setStartDate] = useState("20240501");
    const [endDate, setEndDate] = useState("20240725");

    function buttonClickHandler() {
        navigate('/stock-list')
    }

    return(
        <StockSectionWrapper>
            <StockSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"}/>
                <StockBoxContainer>
                    <IntroductionBox
                        onClick={buttonClickHandler}
                        subTitle={"AI 모델 성과"}
                        title={"24년 7월 2주차\nTrand Trader\n선정 주식 종목"}
                        content={"서비스에서 선정된\n자산별 현황을 확인해보세요"}
                        detail={"⦁ 종목은 매 주 시가총액 상위 10개 종목으로 선정됩니다."}
                        buttonText={"10 종목 모두 확인하러가기"}/>
                    <StockBox
                        stockNumber={"1"}
                        stockTitle={"우리금융지주"}
                        stockCode={"KR7316140003"}
                        startDate={startDate}
                        endDate={endDate}
                    />
                    <StockBox
                        stockNumber={"2"}
                        stockTitle={"삼성전자"}
                        stockCode={"KR7005930003"}
                        startDate={startDate}
                        endDate={endDate}
                    />
                    <StockBox
                        stockNumber={"3"}
                        stockTitle={"SK하이닉스"}
                        stockCode={"KR7000660001"}
                        startDate={startDate}
                        endDate={endDate}
                    />
                </StockBoxContainer>
            </StockSectionContainer>
        </StockSectionWrapper>
    );
};

export default StockSection;