import React, { useState } from "react";
import styled from "styled-components";
import HeaderContainer from "./containers/headerContainer/HeaderContainer";
import StockIndexContainer from "./containers/mainContainer/StockIndexContainer";
import StockDetailContainer from "./containers/bottomContainer/StockDetailContainer";
import StockChartContainer from "./containers/mainContainer/StockChartContainer";
import StockSummaryContainer from "./containers/bottomContainer/StockSummaryContainer";
import ContainerChangeButtonContainer from "./containers/bottomContainer/ContainerChangeButtonContainer";
import StockRecommendDetailContainer from "./containers/bottomContainer/StockRecommendDetailContainer";

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

    return (
        <Container>
            <InnerContainer>
                <HeaderContainer />
                <StockIndexContainer />
                <StockChartContainer />
                <ContainerChangeButtonContainer
                    selectedComponent={selectedComponent}
                    setSelectedComponent={setSelectedComponent}
                />
                {selectedComponent === "summary" && <StockSummaryContainer />}
                {selectedComponent === "detail" && <StockDetailContainer />}
                {selectedComponent === "recommend" && <StockRecommendDetailContainer />}
            </InnerContainer>
        </Container>
    );
};

export default ComputerMainPage;
