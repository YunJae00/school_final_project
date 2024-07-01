import React from "react";
import styled from "styled-components";
import StockIndexComponent from "../components/StockIndexComponent";

const Container = styled.div`
    display: flex;
    height: 80px;
    gap: 30px;
`;

const StockIndexContainer = () => {
    return(
        <Container>
            <StockIndexComponent indexText={"KOSPI"} indexPrice={"2742.00"} indexFluctuation={"35.03(1.29)"}/>
            <StockIndexComponent indexText={"KOSPI"} indexPrice={"2742.00"} indexFluctuation={"35.03(1.29)"}/>
            <StockIndexComponent indexText={"KOSPI"} indexPrice={"2742.00"} indexFluctuation={"35.03(1.29)"}/>
            <StockIndexComponent indexText={"KOSPI"} indexPrice={"2742.00"} indexFluctuation={"35.03(1.29)"}/>
            <StockIndexComponent indexText={"KOSPI"} indexPrice={"2742.00"} indexFluctuation={"35.03(1.29)"}/>
            <StockIndexComponent indexText={"KOSPI"} indexPrice={"2742.00"} indexFluctuation={"35.03(1.29)"}/>
        </Container>
    );
}

export default StockIndexContainer;