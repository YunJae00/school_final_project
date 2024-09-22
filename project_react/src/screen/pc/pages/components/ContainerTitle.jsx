import React from "react";
import styled from "styled-components";

const TitleWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    gap: 0.5rem;
`;

const Title = styled.span`
    color: white;
    font-family: pretendard,serif;
    font-size: 2.25rem;
`;

const ContainerTitle = ({subTitle}) => {
    return(
        <TitleWrapper>
            <Title style={{display: "flex"}}><Title style={{fontWeight: "bold", fontFamily: "Inter"}}>STOPICKR</Title>에서</Title>
            <Title>{subTitle}</Title>
        </TitleWrapper>
    );
};

export default ContainerTitle;