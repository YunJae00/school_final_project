import React from "react";
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

const StockDetailSection = () => {
    return(
        <StockDetailSectionWrapper>
            <StockDetailSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"}/>
                <StockDetailBoxContainer>
                    <StockDetailBoxRow>
                        <IntroductionBox
                            flex={2}
                            subTitle={"AI 모델 성과"}
                            title={"24년 7월 2주차\nTrand Trader\n선정 주식 종목"}
                            content={"서비스에서 선정된\n자산별 현황을 확인해보세요"}
                            detail={"⦁ 종목은 매 주 시가총액 상위 10개 종목으로 선정됩니다."}
                            buttonText={"10 종목 모두 확인하러가기"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                        <StockBox
                            stockNumber={"1"}
                            stockTitle={"삼성전자"}
                            stockCode={"005960"}
                            stockPrice={"56000"}
                            stockDate={"2024.07.17"}/>
                    </StockDetailBoxRow>
                </StockDetailBoxContainer>
            </StockDetailSectionContainer>
        </StockDetailSectionWrapper>
    );
};

export default StockDetailSection;