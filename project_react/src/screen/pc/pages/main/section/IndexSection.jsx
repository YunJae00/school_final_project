import React, { useState, useEffect } from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import IndexBox from "../../components/IndexBox";
import { executeGetLatestIndexData } from "../../../../../api/ApiService"; // API 호출 함수 import

const IndexSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #264653;
`;

const IndexSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const IndexBoxContainer = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    gap: 1.875rem;
`;

const IndexColumn = styled.div`
    display: flex;
    flex: 1;
    flex-direction: column;
    gap: 1.875rem;
`;

const IndexSection = () => {
    const [indexData, setIndexData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await executeGetLatestIndexData(); // API 호출
                setIndexData(response.data); // API 응답 데이터를 상태로 설정
            } catch (error) {
                console.error("Error fetching index data:", error);
            }
        };

        fetchData();
    }, []);

    return (
        <IndexSectionWrapper>
            <IndexSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천과 함께 지수정보를 확인하세요!"} />
                <IndexBoxContainer>
                    <IndexColumn>
                        <IntroductionBox
                            subTitle={"지수 정보"}
                            title={"24년 7월 2주차\n지수정보 한눈에 보기"}
                            content={"지수정보를 확인해보세요"}
                            flex={1}
                        />
                    </IndexColumn>
                    <IndexColumn>
                        {indexData.slice(0, 2).map((index, idx) => (
                            <IndexBox
                                key={idx}
                                indexTitle={index.indexName}
                                indexPrice={index.price}
                                indexDate={index.date}
                            />
                        ))}
                    </IndexColumn>
                    <IndexColumn>
                        {indexData.slice(2, 4).map((index, idx) => (
                            <IndexBox
                                key={idx}
                                indexTitle={index.indexName}
                                indexPrice={index.price}
                                indexDate={index.date}
                            />
                        ))}
                    </IndexColumn>
                    <IndexColumn>
                        {indexData.slice(4, 6).map((index, idx) => (
                            <IndexBox
                                key={idx}
                                indexTitle={index.indexName}
                                indexPrice={index.price}
                                indexDate={index.date}
                            />
                        ))}
                    </IndexColumn>
                </IndexBoxContainer>
            </IndexSectionContainer>
        </IndexSectionWrapper>
    );
};

export default IndexSection;
