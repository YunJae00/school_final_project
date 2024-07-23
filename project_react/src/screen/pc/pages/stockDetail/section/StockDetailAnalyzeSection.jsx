import React from "react";
import styled from "styled-components";

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

const StockDetailAnalyzeSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #99B5F9;
    padding: 0 0 3.75rem 0;
`;

const StockDetailAnalyzeSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100.125rem;
    padding: 0 0 3.75rem 0;
    gap: 1.875rem;
    background-color: white;
    border-radius: 2.25rem;
    overflow: hidden;
`;

const StockTitleContainer = styled.p`
    display: flex;
    color: white;
    background-color: #264653;
    padding: 2.5rem;
    font-size: 2rem;
    font-weight: bold;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const StockDetail = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 1.875rem;
    padding: 1.25rem 2.5rem 2.5rem 2.5rem;
`;

const StockReturnsPercentBox = styled.div`
    display: flex;
    flex-direction: column;
    flex: 1;
    justify-content: center;
    align-items: center;
    gap: 1.875rem;
`;

const StockReturnsPercentText = styled.p`
    font-size: 6rem;
    font-family: pretendard;
    font-weight: bold;
    color: red;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const StockAIRecommendText = styled.p`
    font-size: 2rem;
    font-family: pretendard;
    font-weight: bold;
    color: red;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const StockAnalyzeValueColumn = styled.div`
    display: flex;
    flex: 2;
    flex-direction: column;
    gap: 1.875rem;
`;

const OperationExplain = styled.div`
    display: flex;
    flex-direction: column;
    padding-top: 1.25rem;
    gap: 1.875rem;
`;

const OperationExplainTitle = styled.p`
    font-size: 2.25rem;
    font-weight: bold;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const OperationExplainSubTitle = styled.p`
    font-size: 2rem;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const OperationExplainContent = styled.p`
    font-size: 1.5rem;
    white-space: pre-line;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const StockAnalyzeValue = ({valueText, value}) => {
    return(
        <div style={{display: "flex", flex: "1", justifyContent: "space-between", padding: "1.25rem", borderRadius: "1.125rem", backgroundColor: "#BEC8CC"}}>
            <p style={{fontSize: "1.5rem", fontFamily: "pretendard", fontWeight: "bold", color: "#5F6466"}}>{wrapTextWithLang(valueText)}</p>
            <p style={{fontSize: "1.5rem", fontFamily: "pretendard", fontWeight: "bold"}}>{wrapTextWithLang(value)}</p>
        </div>
    );
};

const StockDetailAnalyzeSection = () => {
    return(
        <StockDetailAnalyzeSectionWrapper>
            <StockDetailAnalyzeSectionContainer>
                <StockTitleContainer>{wrapTextWithLang("삼성전자 AI 기반 주식 분석")}</StockTitleContainer>
                <StockDetail>
                    <StockReturnsPercentBox>
                        <StockReturnsPercentText>20%</StockReturnsPercentText>
                        <StockAIRecommendText>{wrapTextWithLang("AI 추천 : HOLD")}</StockAIRecommendText>
                    </StockReturnsPercentBox>
                    <StockAnalyzeValueColumn>
                        <StockAnalyzeValue valueText={"초기 자본금"} value={'10,000,000'}/>
                        <StockAnalyzeValue valueText={"테스트 시작일"} value={'10,000,000'}/>
                        <StockAnalyzeValue valueText={"테스트 종료일"} value={'10,000,000'}/>
                    </StockAnalyzeValueColumn>
                    <StockAnalyzeValueColumn>
                        <StockAnalyzeValue valueText={"종료 후 자본금"} value={'10,000,000'}/>
                        <StockAnalyzeValue valueText={"거래 단위"} value={'10,000,000'}/>
                        <StockAnalyzeValue valueText={"테스트 횟수"} value={'10,000,000'}/>
                    </StockAnalyzeValueColumn>
                </StockDetail>
                <OperationExplain>
                    <OperationExplainTitle>{wrapTextWithLang("서비스 운영 방식")}</OperationExplainTitle>
                    <OperationExplainSubTitle>매수/매도 전략</OperationExplainSubTitle>
                    <OperationExplainContent>
                        AI 모델은 과거 데이터와 현재 시장 상황을 분석하여 다음 날의 주가 움직임을 예측합니다. 예측 결과에 따라 매수, 매도, 또는 보유 결정을 내립니다. 예를 들어, 주가가 상승할 것으로 예상되면 매수, 하락할 것으로 예상되면 매도합니다.
                    </OperationExplainContent>
                </OperationExplain>
            </StockDetailAnalyzeSectionContainer>
        </StockDetailAnalyzeSectionWrapper>
    );
};

export default StockDetailAnalyzeSection;