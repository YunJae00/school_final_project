import React, {useEffect, useState} from "react";
import styled from "styled-components";
import icon from "../../../images/logo.png"
import profileIcon from "../../../images/profile_icon.png"
import {useAuth} from "../security/AuthContext";
import {useNavigate} from "react-router-dom";

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    height: 80px;
    padding-right: 10px;
    border-bottom: solid #486284 5px;
`;

const Icon = styled.img`
    width: 80px;
    height: 80px;
`;

const ProfileIcon = styled.img`
    width: 45px;
    height: 45px;
    opacity: 0.7; 
`;

const LogInButton = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80px;
    height: 60%;
    background-color: #486284;
    border-radius: 50px;
    cursor: pointer;

    &:hover {
        background-color: #355272;
    }
`;

const HeaderContainer = () => {

    const authContext = useAuth();
    const navigate = useNavigate();

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        if(authContext.isAuthenticated){
            setIsLoggedIn(true);
        }
        else{
            setIsLoggedIn(false);
        }
    }, [handleLogin, handleLogout]);

    function handleLogin(){
        navigate('/login');
    }

    function handleLogout() {
        authContext.logout();
        navigate('/login');
    }

    return (
        <Container>
            <Icon src={icon} alt="icon"/>
            <div style={{display: "flex", justifyContent: "center", alignItems: "center", gap: "20px"}}>
                {!isLoggedIn &&
                    <LogInButton onClick={handleLogin}>
                        <p style={{color: "white"}}>Login</p>
                    </LogInButton>
                }
                {isLoggedIn &&
                    <LogInButton onClick={handleLogout}>
                        <p style={{color: "white"}}>Logout</p>
                    </LogInButton>
                }
                <ProfileIcon src={profileIcon} alt="profileIcon"/>
            </div>
        </Container>
    );
}

export default HeaderContainer;