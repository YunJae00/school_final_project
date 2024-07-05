import React from "react";
import styled from "styled-components";

const ButtonContainer = styled.div`
    display: flex;
    justify-content: center;
    gap: 32px;
`;

const Button = styled.button`
    width: 378px;
    padding: 10px 20px;
    font-size: 16px;
    font-weight: bold;
    background-color: ${(props) => (props.selected ? "#253953" : "white")};
    color: ${(props) => (props.selected ? "white" : "black")};
    border: ${(props) => (props.selected ? "none" : "1px solid #ccc")};
    border-radius: 5px;
    cursor: pointer;
    &:hover {
        background-color: #253953;
        color: white;
    }
`;

const ContainerChangeButtonContainer = ({ selectedComponent, setSelectedComponent }) => {
    return (
        <ButtonContainer>
            <Button
                selected={selectedComponent === "summary"}
                onClick={() => setSelectedComponent("summary")}
            >
                내 그룹 & 시장 요약 보기
            </Button>
            <Button
                selected={selectedComponent === "detail"}
                onClick={() => setSelectedComponent("detail")}
            >
                한국 주식 자세히 보기
            </Button>
            <Button
                selected={selectedComponent === "recommend"}
                onClick={() => setSelectedComponent("recommend")}
            >
                AI기반 주식 수익 자세히 보기
            </Button>
        </ButtonContainer>
    );
};

export default ContainerChangeButtonContainer;
