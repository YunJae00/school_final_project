import React, {useState} from "react";
import styled from "styled-components";
import HeaderContainer from "./containers/HeaderContainer";
import StockIndexContainer from "./containers/StockIndexContainer";
import StockDetailContainer from "./containers/StockDetailContainer";
import StockRecommendContainer from "./containers/StockRecommendContainer";
import StockSummaryContainer from "./containers/StockSummaryContainer";
import SelectButtonContainer from "./containers/SelectButtonContainer";

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

    const [selectedComponent, setSelectedComponent] = useState("summary");

    return(
        <Container>
            <InnerContainer>
                <HeaderContainer/>
                <StockIndexContainer/>
                <StockRecommendContainer/>
                <SelectButtonContainer selectedComponent={selectedComponent} setSelectedComponent={setSelectedComponent}/>
                {selectedComponent === "summary" ? <StockSummaryContainer /> : <StockDetailContainer />}
            </InnerContainer>
        </Container>
    );
}

export default ComputerMainPage;