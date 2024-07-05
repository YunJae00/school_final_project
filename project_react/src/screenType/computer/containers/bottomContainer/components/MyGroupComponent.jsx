import styled from "styled-components";
import StockSummaryListComponent from "./StockSummaryListComponent";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
    align-items: start;
    width: 378px;
    background-color: #253953;
    border-radius: 8px;
    padding: 20px;
    gap: 15px;
`;

const SummaryTitle = styled.p`
    font-weight: bold;
    color: white;
    font-size: 17px;
    margin-bottom: 10px;
`;

const NavigateContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    gap: 30px;
`;

const LinkText = styled.a`
    color: white;
    cursor: pointer;
    text-decoration: underline;
    font-size: 14px;
    &:hover {
        color: #5bd167;
    }
`;

const MyGroupComponent = ({title, summaryList}) => {

    const navigate = useNavigate();
    const localStorageLogIn = localStorage.getItem("isLoggedIn");

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    const loginHandler = () => {
        navigate('/login');
    };

    useEffect(() => {
        if (localStorageLogIn === "true")
            setIsLoggedIn(true)
    }, [localStorageLogIn]);

    return(
        <Container>
            <SummaryTitle>{title}</SummaryTitle>
            {isLoggedIn &&
                <StockSummaryListComponent stockTitle={"램테크놀러지"} stockPrice={"5910 KRW"} stockFluctuation={"+28.98%"}/>
            }
            {!isLoggedIn &&
                <NavigateContainer>
                    <p style={{fontSize: "23px", color: "white", fontWeight: "bold"}}>로그인이 필요한 서비스 입니다</p>
                    <LinkText onClick={loginHandler}>로그인 페이지로 이동</LinkText>
                </NavigateContainer>
            }
        </Container>
    );
}

export default MyGroupComponent;