import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 200px;
    height: 80px;
    background-color: #486284;
    border-radius: 8px;
    gap: 18px;
`;

const IndexText = styled.p`
    font-weight: bold;
    color: white;
    font-size: 20px;
    white-space: pre-line;
    text-align: center;
`;

const InnerContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 5px;
`;


const StockIndexComponent = ({ indexText, indexPrice, indexFluctuation }) => {
    const isPositive = parseFloat(indexFluctuation) > 0;
    const fluctuationColor = isPositive ? "red" : "blue";

    return (
        <Container>
            <IndexText>{indexText}</IndexText>
            <InnerContainer>
                <p style={{ fontSize: "14px", color: "white", opacity: "0.5" }}>{indexPrice}</p>
                <p style={{ fontSize: "13px", color: fluctuationColor }}>
                    {indexFluctuation.startsWith("+") || indexFluctuation.startsWith("-0") ? indexFluctuation : `+${indexFluctuation}`}
                </p>
            </InnerContainer>
        </Container>
    );
}

export default StockIndexComponent;
