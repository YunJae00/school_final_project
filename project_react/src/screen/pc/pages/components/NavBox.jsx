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

const NavBoxWrapper = styled.div`
    display: flex;
    flex: 1;
    flex: ${props => (props.flex ? props.flex : '1')};
    justify-content: space-between;
    height: 21.875rem;
    align-items: start;
    border-radius: 2.25rem;
    background-color: ${props => (props.navBackgroundColor ? props.navBackgroundColor : "white")};
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
    width: 21.875rem;
    height: 21.875rem;
    position: absolute;  
    bottom: 2.5rem;           
    right: 2.5rem;
`;

const NavBox = ({ flex, navBackgroundColor, navSubTitle, navTitle, navContent, navButton, navImage }) => {
    return (
        <NavBoxWrapper flex={flex} navBackgroundColor={navBackgroundColor}>
            <NavBoxLeft>
                <NavBoxLeftInner>
                    <NavSubTitle>{wrapTextWithLang(navSubTitle)}</NavSubTitle>
                    <NavTitle style={{ fontWeight: 'bold' }}>{wrapTextWithLang(navTitle)}</NavTitle>
                    <NavContent>{wrapTextWithLang(navContent)}</NavContent>
                </NavBoxLeftInner>
                <BlueButton buttonText={navButton} />
            </NavBoxLeft>
            {navImage && <NavBoxImage src={navImage} alt={"image"}/>}
        </NavBoxWrapper>
    );
};

export default NavBox;
