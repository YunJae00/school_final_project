import React from "react";
import styled from "styled-components";
import BlueButton from "./BlueButton";

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

// $navBackgroundColor로 prop을 전달하여 DOM 요소로 전달되지 않도록 설정
const NavBoxWrapper = styled.div`
    display: flex;
    flex: 1;
    justify-content: space-between;
    height: 20rem;
    align-items: start;
    border-radius: 2.25rem;
    background-color: ${({ $navBackgroundColor }) => $navBackgroundColor || "white"};
    padding: 2.5rem;
    position: relative;
`;

const NavBoxLeft = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    justify-content: space-between;
    align-items: start;
`;

const NavBoxLeftInner = styled.div`
    display: flex;
    flex-direction: column;
    gap: 0.8rem;
`;

const NavSubTitle = styled.p`
    font-size: 1rem;
    white-space: pre-line;
    color: white;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const NavTitle = styled.p`
    font-size: 2rem;
    white-space: pre-line;
    color: white;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const NavContent = styled.p`
    font-size: 1.25rem;
    white-space: pre-line;
    color: white;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const NavBoxImage = styled.img`
    width: 12rem;
    height: 12rem;
    position: absolute;
    bottom: 2.5rem;
    right: 2.5rem;
`;

const NavBox = ({ navBackgroundColor, navSubTitle, navTitle, navContent, navButton, navImage }) => {
    return (
        <NavBoxWrapper $navBackgroundColor={navBackgroundColor}>
            <NavBoxLeft>
                <NavBoxLeftInner>
                    <NavSubTitle>{wrapTextWithLang(navSubTitle)}</NavSubTitle>
                    <NavTitle style={{ fontWeight: 'bold' }}>{wrapTextWithLang(navTitle)}</NavTitle>
                    <NavContent>{wrapTextWithLang(navContent)}</NavContent>
                </NavBoxLeftInner>
                <BlueButton buttonText={navButton} />
            </NavBoxLeft>
            {navImage && <NavBoxImage src={navImage} alt={"image"} />}
        </NavBoxWrapper>
    );
};

export default NavBox;
