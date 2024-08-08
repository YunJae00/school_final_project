import React, { useState } from "react";
import styled from "styled-components";

const wrapTextWithLang = (text) => {
    if (!text) return null; // text가 undefined나 null인 경우 아무것도 반환하지 않음
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
    color: ${({ profit }) => (profit >= 0 ? 'red' : 'blue')};
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
    padding: 1.25rem 2.5rem;
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
    line-height: 1.3;
    [lang="en"] {
        font-family: 'Inter', sans-serif;
    }
    [lang="ko"] {
        font-family: 'pretendard', sans-serif;
    }
`;

const StockAnalyzeValue = ({ valueText, value }) => {
    return (
        <div style={{ display: "flex", flex: "1", justifyContent: "space-between", padding: "1.25rem", borderRadius: "1.125rem", backgroundColor: "#BEC8CC" }}>
            <p style={{ fontSize: "1.5rem", fontFamily: "pretendard", fontWeight: "bold", color: "#5F6466" }}>{wrapTextWithLang(valueText)}</p>
            <p style={{ fontSize: "1.5rem", fontFamily: "pretendard", fontWeight: "bold" }}>{wrapTextWithLang(value)}</p>
        </div>
    );
};

const StockDetailAnalyzeSection = ({ testResult, predictionResult }) => {
    if (!testResult || !predictionResult) {
        return <p>Loading...</p>; // 데이터를 불러오는 동안 표시할 내용
    }

    const initialCapital = 1000000;
    const endCapital = initialCapital+testResult.averageProfit;
    const testCount = '10회'
    const tradingUnit = '10주';
    const profitRate = ((endCapital - initialCapital) / initialCapital) * 100;

    const formattedEndCapital = testResult.endCapital ? testResult.endCapital.toLocaleString() : "N/A";
    const formattedAverageProfit = profitRate !== undefined ? `${profitRate}%` : "N/A";

    return (
        <StockDetailAnalyzeSectionWrapper>
            <StockDetailAnalyzeSectionContainer>
                <StockTitleContainer>{wrapTextWithLang("삼성전자 AI 기반 주식 분석")}</StockTitleContainer>
                <StockDetail>
                    <StockReturnsPercentBox>
                        <StockReturnsPercentText profit={testResult.averageProfit}>
                            {wrapTextWithLang(formattedAverageProfit)}
                        </StockReturnsPercentText>
                        <StockAIRecommendText>{wrapTextWithLang(`AI 추천 : ${predictionResult.action}`)}</StockAIRecommendText>
                    </StockReturnsPercentBox>
                    <StockAnalyzeValueColumn>
                        <StockAnalyzeValue valueText={"초기 자본금"} value={initialCapital.toLocaleString()} />
                        <StockAnalyzeValue valueText={"테스트 시작일"} value={testResult.startDate || "N/A"} />
                        <StockAnalyzeValue valueText={"테스트 종료일"} value={testResult.endDate || "N/A"} />
                    </StockAnalyzeValueColumn>
                    <StockAnalyzeValueColumn>
                        <StockAnalyzeValue valueText={"종료 후 자본금"} value={endCapital.toLocaleString()} />
                        <StockAnalyzeValue valueText={"거래 단위"} value={tradingUnit.toLocaleString()} />
                        <StockAnalyzeValue valueText={"테스트 횟수"} value={testCount.toLocaleString()} />
                    </StockAnalyzeValueColumn>
                </StockDetail>
                <OperationExplain>
                    <OperationExplainTitle>{wrapTextWithLang("서비스 운영 방식")}</OperationExplainTitle>
                    <OperationExplainSubTitle>{wrapTextWithLang("매수/매도 전략")}</OperationExplainSubTitle>
                    <OperationExplainContent>{wrapTextWithLang("AI 모델은 과거 데이터와 현재 시장 상황을 분석하여 다음 날의 주가 움직임을 예측합니다. 예측 결과에 따라 매수, 매도, 또는 보유 결정을 내립니다.")}</OperationExplainContent>
                </OperationExplain>
            </StockDetailAnalyzeSectionContainer>
        </StockDetailAnalyzeSectionWrapper>
    );
};

export default StockDetailAnalyzeSection;
