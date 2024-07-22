import React from "react";
import styled from "styled-components";

const IndexBoxWrapper = styled.div`
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: space-between;
    align-items: start;
    border-radius: 2.25rem;
    padding: 2.5rem;
    background-color: white;
`;

const IndexTitle = styled.p`
    font-size: 2rem;
    font-weight: bold;
    font-family: pretendard;
`;

const IndexDetailBox = styled.div`
    display: flex;
    width: 100%;
    justify-content: space-between;
    align-items: center;
`;

const IndexPrice = styled.p`
    font-size: 1.25rem;
    font-family: pretendard;
`;

const IndexDate = styled.p`
    font-size: 1.25rem;
    font-family: pretendard;
`;

const IndexBox = ({indexTitle, indexPrice, indexDate}) => {
    return(
        <IndexBoxWrapper>
            <IndexTitle>{indexTitle}</IndexTitle>
            <IndexDetailBox>
                <IndexPrice>현재가</IndexPrice>
                <IndexPrice>{indexPrice}</IndexPrice>
            </IndexDetailBox>
            <IndexDetailBox>
                <IndexDate>기준일</IndexDate>
                <IndexDate>{indexDate}</IndexDate>
            </IndexDetailBox>
        </IndexBoxWrapper>
    );
};

export default IndexBox;