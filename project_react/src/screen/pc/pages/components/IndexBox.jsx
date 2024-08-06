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
    gap: 1rem;
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

const formatDate = (dateString) => {
    // 이미 YYYY-MM-DD 형식이라면 그대로 사용
    if (dateString.includes('-')) {
        return dateString;
    }

    // YYYYMMDD 또는 다른 형식의 문자열을 YYYY-MM-DD로 변환
    const year = dateString.slice(0, 4);
    const month = dateString.slice(4, 6);
    const day = dateString.slice(6, 8);
    return `${year}-${month}-${day}`;
};

const IndexBox = ({ indexTitle, indexPrice, indexDate }) => {
    return (
        <IndexBoxWrapper>
            <IndexTitle>{indexTitle}</IndexTitle>
            <IndexDetailBox>
                <IndexPrice>현재가</IndexPrice>
                <IndexPrice>{indexPrice}</IndexPrice>
            </IndexDetailBox>
            <IndexDetailBox>
                <IndexDate>기준일</IndexDate>
                <IndexDate>{formatDate(indexDate)}</IndexDate> {/* 날짜 형식 변환 */}
            </IndexDetailBox>
        </IndexBoxWrapper>
    );
};

export default IndexBox;
