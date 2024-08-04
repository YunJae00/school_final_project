import React, { useEffect, useState } from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import StockBox from "../../components/StockBox";
import { executeGetWeeklyStocksForList } from "../../../../../api/ApiService";

const StockDetailSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #99B5F9;
`;

const StockDetailSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const StockDetailBoxContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
    justify-content: center;
    gap: 1.875rem;
`;

const StockDetailBoxRow = styled.div`
    display: flex;
    width: 100%;
    gap: 1.875rem;
`;

const StockListSection = () => {
    const [stocks, setStocks] = useState([]); // 주식 데이터를 저장할 상태 변수
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    useEffect(() => {
        // API 호출
        executeGetWeeklyStocksForList()
            .then(response => {
                const sortedStocks = response.data.sort((a, b) => new Date(b.date) - new Date(a.date)); // 날짜 기준으로 내림차순 정렬
                const latestDate = sortedStocks[0]?.date; // 가장 최근 날짜 가져오기
                const latestStocks = sortedStocks.filter(stock => stock.date === latestDate); // 가장 최근 날짜의 주식 데이터 필터링
                setStocks(latestStocks); // 최근 날짜 데이터 전체를 상태로 설정
                setStartDate(latestDate);
                setEndDate(latestDate);
            })
            .catch(error => {
                console.error('Error fetching weekly stocks:', error);
            });
    }, []);

    return (
        <StockDetailSectionWrapper>
            <StockDetailSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천을 알아보고 투자하세요!"} />
                <StockDetailBoxContainer>
                    <StockDetailBoxRow>
                        <div style={{ display: "flex", flex: "1", gap: "1.875rem" }}>
                            <IntroductionBox
                                subTitle={"AI 모델 성과"}
                                title={"24년 7월 2주차\nTrand Trader 선정 주식 종목"}
                                content={"1. 서비스에서 선정된 자산별 현황을 확인해보세요\n"
                                    + "2. 선정된 종목을 확인하고 개인 포트폴리오에 추가해보세요\n"
                                    + "3. 선정된 종목의 AI 분석을 확인해보세요"}
                                detail={"⦁ 종목은 매 주 시가총액 상위 10개 종목으로 선정됩니다."} />
                        </div>
                        <div style={{ display: "flex", flex: "1", gap: "1.875rem" }}>
                            {stocks.slice(0, 2).map((stock, index) => (
                                <StockBox
                                    key={index}
                                    stockNumber={index + 1}
                                    stockTitle={stock.itmsNm}
                                    stockCode={stock.isinCd}
                                    startDate={startDate}
                                    endDate={endDate}
                                />
                            ))}
                        </div>
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        {stocks.slice(2, 6).map((stock, index) => (
                            <StockBox
                                key={index + 2}
                                stockNumber={index + 3}
                                stockTitle={stock.itmsNm}
                                stockCode={stock.isinCd}
                                startDate={startDate}
                                endDate={endDate}
                            />
                        ))}
                    </StockDetailBoxRow>
                    <StockDetailBoxRow>
                        {stocks.slice(6, 10).map((stock, index) => (
                            <StockBox
                                key={index + 6}
                                stockNumber={index + 7}
                                stockTitle={stock.itmsNm}
                                stockCode={stock.isinCd}
                                startDate={startDate}
                                endDate={endDate}
                            />
                        ))}
                    </StockDetailBoxRow>
                </StockDetailBoxContainer>
            </StockDetailSectionContainer>
        </StockDetailSectionWrapper>
    );
};

export default StockListSection;
