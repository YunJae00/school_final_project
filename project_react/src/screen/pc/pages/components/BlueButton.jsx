import styled from "styled-components";
import React from "react";

const wrapTextWithLang = (text) => {
    return text.split('\n').map((line, index) => (
        <React.Fragment key={index}>
            {line.split('').map((char, index) => {
                const isKorean = /[가-힣]/.test(char);
                const lang = isKorean ? 'ko' : 'en';
                return <span key={index} lang={lang}>{char}</span>;
            })}
            <br />
        </React.Fragment>
    ));
};

const ButtonWrapper = styled.div`
    font-size: 1rem;
    font-weight: bold;
    text-align: center;
    white-space: pre-line;
    color: white;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
    cursor: pointer;
    border-radius: 30px;
    padding: 0.625rem 1.2rem;
    background-color: #1454FF;
`;

const BlueButton = ({buttonText, onClick}) => {
    return(
        <ButtonWrapper onClick={onClick}>{wrapTextWithLang(buttonText)}</ButtonWrapper>
    );
};

export default BlueButton;