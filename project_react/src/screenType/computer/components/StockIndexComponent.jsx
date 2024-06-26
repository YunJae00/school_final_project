import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 174px;
    height: 100%;
    background-color: #486284;
    border-radius: 13px;
    gap: 18px;
`;

const IndexText = styled.p`
    font-weight: bold;
    color: white;
    font-size: 20px;
`;

const InnerContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 10px;
`;

const StockIndexComponent = ({indexText, indexPrice, indexFluctuation}) => {
    return(
        <Container>
            <IndexText>{indexText}</IndexText>
            <InnerContainer>
                <p style={{fontSize: "15px", color: "white", opacity: "0.5"}}>{indexPrice}</p>
                <p style={{fontSize: "14px", color: "red"}}>{indexFluctuation}</p>
            </InnerContainer>
        </Container>
    );
}

export default StockIndexComponent;