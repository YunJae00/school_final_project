import React, { useState } from "react";
import styled from "styled-components";
import logo from "../../images/logo.png";
import LoginContainer from "./containers/auth/LoginContainer";
import SignupContainer from "./containers/auth/SignupContainer";

const Container = styled.div`
    display: flex;
    height: 90vh;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const HeaderContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 10px;
`;

const ComputerLoginPage = () => {
    const [isLogin, setIsLogin] = useState(true);

    const toggleMode = () => {
        setIsLogin(!isLogin);
    };

    return (
        <Container>
            <HeaderContainer>
                <img src={logo} alt="logoImage" style={{ width: "100px", height: "100px", marginRight: "20px" }} />
                <p style={{ color: "#2A5E9F", fontWeight: "bold", fontSize: "45px" }}>T</p>
                <p style={{ fontSize: "45px", fontWeight: "bold" }}>rend </p>
                <p style={{ color: "#5BD167", fontWeight: "bold", fontSize: "45px" }}>T</p>
                <p style={{ fontSize: "45px", fontWeight: "bold" }}>rader</p>
            </HeaderContainer>
            {isLogin ? (
                <LoginContainer onSignupClick={toggleMode} />
            ) : (
                <SignupContainer onLoginClick={toggleMode} />
            )}
        </Container>
    );
};

export default ComputerLoginPage;
