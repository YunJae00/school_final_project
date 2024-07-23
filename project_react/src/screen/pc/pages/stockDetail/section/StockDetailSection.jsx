import React from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import BlueButton from "../../components/BlueButton";

const StockDetailSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #99B5F9;
`;

const StockDetailSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const StockDetail = styled.div`
    display: flex;
    padding: 2.5rem;
    gap: 1.875rem;
    background-color: white;
    border-radius: 2.25rem;
`;

const StockDetailLeft = styled.div`
    display: flex;
    flex-direction: column;
    flex: 3;
`;

const StockDetailRight = styled.div`
    display: flex;
    flex-direction: column;
    flex: 1;
`;

const StockTitleBox = styled.div`
    display: flex;
    width: 100%;
    justify-content: start;
    align-items: center;
    gap: 1.875rem;
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

`;

const StockDetailSummary = styled.div`
    display: flex;
    flex-direction: column;
    border-radius: 2.25rem;
    background-color: #264653;
    padding: 1.875rem;
    gap: 1.25rem;
`;

const StockDetailTitle = styled.p`
    width: 100%;
    text-align: center;
    font-size: 2rem;
    font-weight: bold;
    font-family: pretendard;
    color: white;
`;

const StockDetailList = styled.div`
    display: flex;
    width: 100%;
    justify-content: space-between;
    align-items: center;
`;

const StockValueText = styled.p`
    font-size: 1.25rem;
    font-family: pretendard;
    color: white;
`;

const StockDetailSection = () => {
    return(
        <StockDetailSectionWrapper>
            <StockDetailSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"}/>
                <StockDetail>
                    <StockDetailLeft>
                        <StockTitleBox>
                            <StockTitle>삼성전자</StockTitle>
                            <StockCode>005960</StockCode>
                            <BlueButton buttonText={"포트폴리오 추가하기"}/>
                        </StockTitleBox>
                        <StockGraph></StockGraph>
                    </StockDetailLeft>
                    <StockDetailRight>
                        <StockDetailSummary>
                            <StockDetailTitle>주식 정보</StockDetailTitle>
                            <StockDetailList>
                                <StockValueText>기준일</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                            <StockDetailList>
                                <StockValueText>종가</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                            <StockDetailList>
                                <StockValueText>고가</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                            <StockDetailList>
                                <StockValueText>저가</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                            <StockDetailList>
                                <StockValueText>변동</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                            <StockDetailList>
                                <StockValueText>변동 %</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                            <StockDetailList>
                                <StockValueText>거래량</StockValueText><StockValueText>2024.07.17</StockValueText>
                            </StockDetailList>
                        </StockDetailSummary>
                    </StockDetailRight>
                </StockDetail>
            </StockDetailSectionContainer>
        </StockDetailSectionWrapper>
    );
};

export default StockDetailSection;