import styled from "styled-components";
import StockSummaryListComponent from "./StockSummaryListComponent";
import React from "react";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    width: 378px;
    height: 100%;
    background-color: #253953;
    border-radius: 13px;
    padding: 20px;
    gap: 15px;
`;

const SummaryTitle = styled.p`
    font-weight: bold;
    color: white;
    font-size: 20px;
    margin-bottom: 10px;
`;

const StockMyGroupComponent = ({title, summaryList}) => {
    return(
        <Container>
            <SummaryTitle>{title}</SummaryTitle>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
        </Container>
    );
}

export default StockMyGroupComponent;