import React from "react";
import styled from "styled-components";
import HeaderContainer from "./containers/HeaderContainer";
import StockIndexContainer from "./containers/StockIndexContainer";
import StockDetailContainer from "./containers/StockDetailContainer";
import StockRecommendContainer from "./containers/StockRecommendContainer";
import StockSummaryContainer from "./containers/StockSummaryContainer";

const Container = styled.div`
    display: flex;
    justify-content: center;
    align-items: start;
    width: 100%;
    height: 100%;
`;

const InnerContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 1194px;
    gap: 10px;
`;

const ComputerMainPage = () => {
    return(
        <Container>
            <InnerContainer>
                <HeaderContainer/>
                <StockIndexContainer/>
                <StockRecommendContainer/>
                <StockSummaryContainer/>
                <StockDetailContainer/>
            </InnerContainer>
        </Container>
    );
}

export default ComputerMainPage;