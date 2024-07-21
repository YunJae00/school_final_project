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
    flex-direction: column;
    justify-content: space-between;
    align-items: start;
    height: 33.125rem;
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
    white-space: pre-line;
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
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const BottomContainer = styled.div`
    display: flex;
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

const IntroductionBox = ({subTitle, title, content, detail, buttonText}) => {
    return(
        <IntroductionBoxWrapper>
            <IntroductionTitleContainer>
                <IntroductionBoxSubTitle>{wrapTextWithLang(subTitle)}</IntroductionBoxSubTitle>
                <IntroductionBoxTitle>{wrapTextWithLang(title)}</IntroductionBoxTitle>
                <IntroductionBoxContent>{wrapTextWithLang(content)}</IntroductionBoxContent>
            </IntroductionTitleContainer>
            <BottomContainer>
                <IntroductionBoxDetail>{wrapTextWithLang(detail)}</IntroductionBoxDetail>
                {buttonText && <BlueButton buttonText={buttonText} />}
            </BottomContainer>
        </IntroductionBoxWrapper>
    );
};

export default IntroductionBox;