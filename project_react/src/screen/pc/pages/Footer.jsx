import React from "react";
import styled from "styled-components";

const FooterWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #E7EAF4;     
    padding: 6.25rem 0 6.25rem 0;
`;

const ContentContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100.125rem;
    gap: 1rem;
`;

const Content = styled.p`
    font-size: 1.1rem;
    line-height: 1.3;
    color: #000000;
    opacity: 0.5;
    font-family: pretendard;
`;

const Footer = () => {
    return(
        <FooterWrapper>
            <ContentContainer>
                <Content>
                    ⦁ 본 서비스는 "자본시장과 금융투자업에 관한 법률"을 준수합니다. 이 법률에 따라 본 서비스는 투자 자문 및 일임업을 수행하지 않으며, 투자에 대한 최종 결정은 사용자가 직접 내리셔야 합니다.
                </Content>
                <Content>
                    ⦁ 본 서비스에서 제공되는 모든 정보와 데이터는 신뢰할 수 있는 출처로부터 수집된 것이며, 최대한 정확하고 최신의 정보를 제공하기 위해 노력하고 있습니다. 그러나, 제공되는 정보의 정확성과 완전성을 보장하지 않으며, 정보의 오류나 누락에 대해 책임을 지지 않습니다.
                </Content>
                <Content>
                    ⦁ 주식 투자는 원금 손실의 위험이 있으며, 과거의 수익률이 미래의 수익률을 보장하지 않습니다. 본 서비스에서 제공되는 AI 분석 및 추천은 투자 결정을 보조하기 위한 참고 자료일 뿐, 절대적인 투자 조언이 아닙니다.
                </Content>
                <Content>
                    ⦁ 사용자는 주식 투자와 관련된 모든 위험을 충분히 이해하고, 자신의 투자 성향과 재무 상황에 맞는 결정을 내리셔야 합니다.
                </Content>
                <Content>
                    ⦁ 본 서비스는 사용자가 본 서비스를 이용하여 투자한 결과에 대해 어떠한 책임도 지지 않습니다. 본 서비스에서 제공되는 정보와 분석은 참고용이며, 최종 투자 결정과 그에 따른 결과는 전적으로 사용자의 책임입니다.
                </Content>
                <Content>
                    ⦁ 본 서비스는 제공되는 정보와 분석의 사용으로 인해 발생하는 어떠한 손실이나 손해에 대해 책임을 지지 않습니다.
                </Content>
                <Content>
                    ⦁ 본 서비스는 "개인정보 보호법"을 준수하여 사용자 정보를 보호합니다. 수집된 개인정보는 투자 분석 및 추천 서비스 제공을 위해서만 사용되며, 사용자의 동의 없이 제3자에게 제공되지 않습니다.
                </Content>
                <Content>
                    ⦁ 본 서비스는 광고 및 제휴를 통해 수익을 창출할 수 있으며, 특정 주식이나 금융 상품에 대한 광고가 포함될 수 있습니다. 그러나, 광고 내용은 본 서비스의 투자 추천과 무관하며, 사용자는 광고에 대해 스스로 판단하셔야 합니다.
                </Content>
                <Content>
                    문의사항 ➤ 개발자 email : 0109548@naver.com
                </Content>
            </ContentContainer>
        </FooterWrapper>
    );
};

export default Footer;