import React, { useState } from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import StockBox from "../../components/StockBox";

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

const StockListSection = () => {
    const [startDate, setStartDate] = useState("20240501");
    const [endDate, setEndDate] = useState("20240725");

    return (
        <StockDetailSectionWrapper>
            <StockDetailSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"} />
                <StockDetailBoxContainer>
                    <StockDetailBoxRow>
                        <div style={{ display: "flex", flex: "1", gap: "1.875rem" }}>
                            <IntroductionBox
                                subTitle={"AI 모델 성과"}
                                title={"24년 7월 2주차\nTrand Trader 선정 주식 종목"}
                                content={"1. 서비스에서 선정된 자산별 현황을 확인해보세요\n"
                                    + "2. 선정된 종목을 확인하고 개인 포트폴리오에 추가해보세요\n"
                                    + "3. 선정된 종목의 AI 분석을 확인해보세요"}
                                detail={"⦁ 종목은 매 주 시가총액 상위 10개 종목으로 선정됩니다."} />
                        </div>
                        <div style={{ display: "flex", flex: "1", gap: "1.875rem" }}>
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
                        </div>
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        <StockBox
                            stockNumber={"3"}
                            stockTitle={"SK하이닉스"}
                            stockCode={"KR7000660001"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                        <StockBox
                            stockNumber={"4"}
                            stockTitle={"두산에너빌리티"}
                            stockCode={"KR7034020008"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                        <StockBox
                            stockNumber={"5"}
                            stockTitle={"신한지주"}
                            stockCode={"KR7055550008"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                        <StockBox
                            stockNumber={"6"}
                            stockTitle={"현대차"}
                            stockCode={"KR7005380001"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        <StockBox
                            stockNumber={"7"}
                            stockTitle={"기업은행"}
                            stockCode={"KR7024110009"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                        <StockBox
                            stockNumber={"8"}
                            stockTitle={"KB금융"}
                            stockCode={"KR7105560007"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                        <StockBox
                            stockNumber={"9"}
                            stockTitle={"하나금융지주"}
                            stockCode={"KR7086790003"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                        <StockBox
                            stockNumber={"10"}
                            stockTitle={"기아"}
                            stockCode={"KR7000270009"}
                            startDate={startDate}
                            endDate={endDate}
                        />
                    </StockDetailBoxRow>
                </StockDetailBoxContainer>
            </StockDetailSectionContainer>
        </StockDetailSectionWrapper>
    );
};

export default StockListSection;
