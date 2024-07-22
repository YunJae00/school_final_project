import React from "react";
import ContainerTitle from "../../components/ContainerTitle";
import styled from "styled-components";
import IntroductionBox from "../../components/IntroductionBox";
import IndexBox from "../../components/IndexBox";

const IndexSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #264653;
`;

const IndexSectionContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: start;
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

const IndexBoxList = styled.div`
    display: flex;
    flex: 4;
    flex-direction: column;
    justify-content: space-between;
    gap: 1.875rem;
`;

const ListRow = styled.div`
    flex: 1;
    display: flex;
    gap: 1.875rem;
`;

const IndexSection = () => {
    return(
        <IndexSectionWrapper>
            <IndexSectionContainer>
                <ContainerTitle subTitle={"다양한 주식 추천과 함께 지수정보를 확인하세요!"}/>
                <IndexBoxContainer>
                    <IntroductionBox
                        subTitle={"지수 정보"}
                        title={"24년 7월 2주차\n지수정보 한눈에 보기"}
                        content={"지수정보를 확인해보세요"} />
                    <IndexBoxList>
                        <ListRow>
                            <IndexBox indexTitle={"KOSPI"} indexPrice={"55000"} indexDate={"2024.07.17"}/>
                            <IndexBox indexTitle={"KOSPI"} indexPrice={"55000"} indexDate={"2024.07.17"}/>
                            <IndexBox indexTitle={"KOSPI"} indexPrice={"55000"} indexDate={"2024.07.17"}/>
                        </ListRow>
                        <ListRow>
                            <IndexBox indexTitle={"KOSPI"} indexPrice={"55000"} indexDate={"2024.07.17"}/>
                            <IndexBox indexTitle={"KOSPI"} indexPrice={"55000"} indexDate={"2024.07.17"}/>
                            <IndexBox indexTitle={"KOSPI"} indexPrice={"55000"} indexDate={"2024.07.17"}/>
                        </ListRow>
                    </IndexBoxList>
                </IndexBoxContainer>
            </IndexSectionContainer>
        </IndexSectionWrapper>
    );
};

export default IndexSection;