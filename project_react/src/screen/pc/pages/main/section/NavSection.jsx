import React from "react";
import styled from "styled-components";
import NavBox from "../../components/NavBox";
import aiImage from "../../../../../images/ai_character.png";

const NavSectionWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
`;

const NavSectionContainer = styled.div`
    display: flex;
    width: 100.125rem;
    padding: 3.75rem 0;
    gap: 1.875rem;
`;

const NavSection = () => {
    return (
        <NavSectionWrapper>
            <NavSectionContainer>
                <NavBox
                    navSubTitle={"AI 모델 성과"}
                    navTitle={"AI 모델 성과 및\n수익률 분석"}
                    navContent={"딥 Q-Learning 알고리즘을 통해\n얻은 성과를 확인해보세요"}
                    navBackgroundColor={"#264653"}
                    navButton={"서비스 자세히보기"}
                    navImage={aiImage}
                />
                <NavBox
                    navSubTitle={"주식 시장 전망"}
                    navTitle={"24년 7월\n한국 주식 시장"}
                    navContent={"서비스에서 선정된 자산별 현황을\n확인해보세요"}
                    navBackgroundColor={"#E8C36A"}
                    navButton={"자세히보기"}
                />
                <NavBox
                    navSubTitle={"포트폴리오 관리"}
                    navTitle={"개인 맞춤형 포트폴리오"}
                    navContent={"당신의 포트폴리오를\n효율적으로 관리하세요"}
                    navBackgroundColor={"#F3A261"}
                    navButton={"로그인하러가기"}
                />
            </NavSectionContainer>
        </NavSectionWrapper>
    );
};

export default NavSection;
