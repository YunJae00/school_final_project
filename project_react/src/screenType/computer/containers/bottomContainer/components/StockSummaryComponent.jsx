import React from "react";
import styled from "styled-components";
import StockSummaryListComponent from "./StockSummaryListComponent";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    width: 378px;
    height: 100%;
    background-color: #486284;
    border-radius: 8px;
    padding: 20px;
    gap: 15px;
`;

const SummaryTitle = styled.p`
    font-weight: bold;
    color: white;
    font-size: 17px;
    margin-bottom: 10px;
`;

const StockSummaryComponent = ({summaryTitle, summaryList}) => {
    return(
        <Container>
            <SummaryTitle>{summaryTitle}</SummaryTitle>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
        </Container>
    );
}

export default StockSummaryComponent;