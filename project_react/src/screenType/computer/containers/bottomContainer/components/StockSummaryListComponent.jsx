import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
`;

const SummaryText = styled.p`
    color: white;
    font-size: 15px;
    opacity: 0.9;
`;

const FluctuationContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 13px;
    width: 80px;
    height: 35px;
    background-color: #089981;
`;

const StockSummaryListComponent = ({stockTitle, stockPrice, stockFluctuation}) => {
    return(
        <Container>
            <SummaryText>{stockTitle}</SummaryText>
            <div style={{display: "flex", justifyContent: "center", alignItems: "center", gap: "10px"}}>
                <SummaryText>{stockPrice}</SummaryText>
                <FluctuationContainer>
                    <SummaryText>{stockFluctuation}</SummaryText>
                </FluctuationContainer>
            </div>
        </Container>
    );
}

export default StockSummaryListComponent;