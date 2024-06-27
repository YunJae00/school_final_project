import React from "react";
import styled from "styled-components";
import HeaderContainer from "./containers/HeaderContainer";
import StockIndexContainer from "./containers/StockIndexContainer";
import StockRecommentContainer from "./containers/StockRecommentContainer";
import StockDetailContainer from "./containers/StockDetailContainer";

const Container = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;
    height: 100%;
`;

const InnerContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 360px;
    gap: 10px;
`;

const PhoneMainPage = () => {
    return(
        <Container>
            <InnerContainer>
                <HeaderContainer/>
                <StockIndexContainer/>
                <StockRecommentContainer/>
                <StockDetailContainer/>
            </InnerContainer>
        </Container>
    );
}

export default PhoneMainPage;