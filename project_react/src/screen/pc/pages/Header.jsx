import React from "react";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";

const HeaderWrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #326AF3;
    opacity: 0.8;
`;

const HeaderContainer = styled.div`
    display: flex;
    height: 100px;
    justify-content: space-between;
    align-items: center;
    width: 100.125rem;
`;

const Title = styled.div`
    color: white;
    font-family: Inter;
    font-size: 2.25rem;
    font-weight: bold;
    cursor: pointer;
`;

const NavBox = styled.div`
    display: flex;
    gap: 30px;
`;

const Nav = styled.div`
    font-family: pretendard;
    font-size: 1.2rem;
    color: white;
    cursor: pointer;
`;

const UserIcon = styled.div`
    
`;

const Header = () => {

    const navigate = useNavigate();

    function titleOnClickHandler() {
        navigate('/');
    }

    return(
        <HeaderWrapper>
            <HeaderContainer>
                <Title onClick={titleOnClickHandler}>STOPICKR</Title>
                <NavBox>
                    <Nav>AI 성과 분석</Nav>
                    <Nav>내 포트폴리오</Nav>
                    <UserIcon>

                    </UserIcon>
                </NavBox>
            </HeaderContainer>
        </HeaderWrapper>
    );
};

export default Header;