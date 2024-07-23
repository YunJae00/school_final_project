import React from "react";
import styled from "styled-components";

const StockDetailAnalyzeSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #99B5F9;
`;

const StockDetailAnalyzeSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const StockDetailAnalyzeSection = () => {
    return(
        <StockDetailAnalyzeSectionWrapper>
            <StockDetailAnalyzeSectionContainer>

            </StockDetailAnalyzeSectionContainer>
        </StockDetailAnalyzeSectionWrapper>
    );
};

export default StockDetailAnalyzeSection;