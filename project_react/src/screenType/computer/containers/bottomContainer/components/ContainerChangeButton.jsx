import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 378px;
    height: 100%;
    border-radius: 8px;
    cursor: pointer;
    background-color: ${props => (props.isSelected ? props.selectedColor : "#FFFFFF")};
    border: ${props => (props.isSelected ? "none" : "1px solid #ccc")};
    color: ${props => (props.isSelected ? "#FFFFFF" : "#000000")};
    transition: background-color 0.3s, color 0.3s, border 0.3s;
`;

const ContainerChangeButton = ({ isSelected, onClick, text, backgroundColor, selectedColor }) => {
    return (
        <Container onClick={onClick} isSelected={isSelected} selectedColor={selectedColor}>
            <p>{text}</p>
        </Container>
    );
}

export default ContainerChangeButton;
