import React from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import StockBox from "../../components/StockBox";

const IndexSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #264653;
`;

const IndexSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const IndexBoxContainer = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    gap: 1.875rem;
`;

const IndexSection = () => {
    return(
        <IndexSectionWrapper>
            <IndexSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천과 함께 지수정보를 확인하세요!"}/>
                <IndexBoxContainer>
                    <IntroductionBox
                        subTitle={"지수 정보"}
                        title={"24년 7월 2주차\n지수정보 한눈에 보기"}
                        content={"지수정보를 확인해보세요"} />
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
                </IndexBoxContainer>
            </IndexSectionContainer>
        </IndexSectionWrapper>
    );
};

export default IndexSection;