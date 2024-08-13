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

const IntroductionBoxWrapper = styled.div`
    display: flex;
    flex: 1;
    flex-direction: column;
    justify-content: space-between;
    align-items: start;
    border-radius: 2.25rem;
    padding: 2.5rem;
    background-color: white;
`;

const IntroductionTitleContainer = styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;

const IntroductionBoxSubTitle = styled.p`
    font-size: 1rem;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const IntroductionBoxTitle = styled.p`
    font-size: 2rem;
    font-weight: bold;
    white-space: pre-line;
    line-height: 1.3;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const IntroductionBoxContent = styled.p`
    font-size: 1.25rem;
    white-space: pre-line;
    line-height: 1.5;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const BottomContainer = styled.div`
    display: flex;
    width: 100%;
    flex-direction: column;
    gap: 10px;
`;

const IntroductionBoxDetail = styled.p`
    font-size: 1rem;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const IntroductionBox = ({subTitle, title, content, detail, buttonText, onClick}) => {
    return(
        <IntroductionBoxWrapper>
            <IntroductionTitleContainer>
                <IntroductionBoxSubTitle>{wrapTextWithLang(subTitle)}</IntroductionBoxSubTitle>
                <IntroductionBoxTitle>{wrapTextWithLang(title)}</IntroductionBoxTitle>
                <IntroductionBoxContent>{wrapTextWithLang(content)}</IntroductionBoxContent>
            </IntroductionTitleContainer>
            <BottomContainer>
                {detail && <IntroductionBoxDetail>{wrapTextWithLang(detail)}</IntroductionBoxDetail>}
                {buttonText && <BlueButton buttonText={buttonText} onClick={onClick}/>}
            </BottomContainer>
        </IntroductionBoxWrapper>
    );
};

export default IntroductionBox;