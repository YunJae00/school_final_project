import React from "react";
import styled from "styled-components";
import DetailSummaryContainerChangeButton from "../components/DetailSummaryContainerChangeButton";

const Container = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 45px;
    gap: 30px;
`;

const SelectButtonContainer = ({ selectedComponent, setSelectedComponent }) => {
    return (
        <Container>
            <DetailSummaryContainerChangeButton
                isSelected={selectedComponent === "summary"}
                onClick={() => setSelectedComponent("summary")}
                text="내 그룹 & 시장 요약 보기"
                selectedColor="#253953"
            />
            <DetailSummaryContainerChangeButton
                isSelected={selectedComponent === "detail"}
                onClick={() => setSelectedComponent("detail")}
                text="한국 주식 자세히 보기"
                selectedColor="#486284"
            />
        </Container>
    );
}

export default SelectButtonContainer;
