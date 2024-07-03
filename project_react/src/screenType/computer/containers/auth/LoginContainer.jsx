import React, { useState } from "react";
import styled from "styled-components";

const LoginInContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 50px 50px;
    width: 500px;
    gap: 15px;
    border-radius: 10px;
    box-shadow: 0 0 10px;
`;

const InputContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 100%;
`;

const InputLabel = styled.p`
    margin: 0;
    padding: 0;
    font-size: 14px;
    font-weight: bold;
    color: black;
    text-align: left;
    margin-bottom: 10px;
`;

const Input = styled.input`
    padding: 10px;
    font-size: 16px;
    border: 1px solid ${(props) => (props.error ? "red" : "#ccc")};
    border-radius: 5px;
    width: calc(100% - 20px);
`;

const Button = styled.button`
    padding: 10px 0px;
    font-size: 16px;
    width: 100%;
    background-color: #2a5e9f;
    //background: linear-gradient(45deg, #5bd167, #2a5e9f);
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
`;

const ErrorMessage = styled.p`
    color: red;
    font-size: 14px;
`;

const LinkText = styled.a`
    color: #2a5e9f;
    cursor: pointer;
    text-decoration: underline;
    font-size: 14px;
    &:hover {
        color: #5bd167;
    }
`;

const LoginContainer = ({ onSignupClick }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(false);

    const handleLoginClick = () => {
        if (!email || !password) {
            setError(true);
        } else {
            setError(false);
            // 로그인 처리 로직 추가
        }
    };

    return (
        <LoginInContainer>
            <p style={{ color: "black", fontWeight: "bold", fontSize: "30px", marginBottom: "20px" }}>로그인</p>
            <InputContainer>
                <InputLabel>이메일</InputLabel>
                <Input
                    type="email"
                    placeholder="이메일 주소를 입력하세요"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    error={error && !email}
                />
            </InputContainer>
            <InputContainer>
                <InputLabel>비밀번호</InputLabel>
                <Input
                    type="password"
                    placeholder="비밀번호를 입력하세요"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    error={error && !password}
                />
            </InputContainer>
            {error && <ErrorMessage>모든 필드를 입력해주세요.</ErrorMessage>}
            <Button onClick={handleLoginClick}>로그인</Button>
            <LinkText onClick={onSignupClick}>회원가입</LinkText>
        </LoginInContainer>
    );
};

export default LoginContainer;
