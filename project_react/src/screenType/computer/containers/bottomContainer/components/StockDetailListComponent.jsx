import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

const DetailContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 30px;
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

const DetailText = styled.p`
    color: white;
    width: 70px;
    font-size: 15px;
    opacity: 0.9;
`;

const StockDetailListComponent = ({ name, price, high, low, change, changePercent, volume }) => {
    return(
        <Container>
            <p style={{width: "100px", color: "white", fontSize: "15px", fontWeight: "bold"}}>{name}</p>
            <DetailContainer>
                <DetailText>{price}</DetailText>
                <DetailText>{high}</DetailText>
                <DetailText>{low}</DetailText>
                <DetailText>{change}</DetailText>
                <FluctuationContainer>
                    <p style={{color: "white", fontSize: "15px"}}>{changePercent}</p>
                </FluctuationContainer>
                <DetailText style={{width: "100px"}}>{volume}</DetailText>
            </DetailContainer>
        </Container>
    );
}

export default StockDetailListComponent;