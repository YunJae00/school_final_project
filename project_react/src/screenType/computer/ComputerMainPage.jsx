import React from "react";
import styled from "styled-components";
import HeaderContainer from "./containers/HeaderContainer";
import StockIndexContainer from "./containers/StockIndexContainer";
import StockDetailContainer from "./containers/StockDetailContainer";
import StockRecommentContainer from "./containers/StockRecommentContainer";

const Container = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;
    height: 100%;
`;

const InnerContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 1194px;
    height: 100vh;
    gap: 10px;
`;

const ComputerMainPage = () => {
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

export default ComputerMainPage;