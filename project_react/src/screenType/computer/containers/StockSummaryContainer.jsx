import React from "react";
import styled from "styled-components";
import StockSummaryComponent from "../components/StockSummaryComponent";
import StockMyGroupComponent from "../components/StockMyGroupComponent";

const Container = styled.div`
    display: flex;
    width: 100%;
    gap: 30px;
`;

const SummaryContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 786px;
    height: 100%;
    background-color: bisque;
    gap: 10px;
`;

const InnerContainer = styled.div`
    display: flex;
    justify-content: center;
    width: 786px;
    height: 100%;
    background-color: bisque;
    gap: 30px;
`;

const StockSummaryContainer = () => {
    return(
        <Container>
            <SummaryContainer>
                <InnerContainer>
                    <StockSummaryComponent summaryTitle={"상승 주식 >"}/>
                    <StockSummaryComponent summaryTitle={"하락 주식 >"}/>
                </InnerContainer>
                <InnerContainer>
                    <StockSummaryComponent summaryTitle={"거래량이 가장 많았던 주식 >"}/>
                    <StockSummaryComponent summaryTitle={"가장 변동성이 높았던 주식 >"}/>
                </InnerContainer>
            </SummaryContainer>
            <StockMyGroupComponent title={"내 그룹 >"}/>
        </Container>
    );
}

export default StockSummaryContainer;