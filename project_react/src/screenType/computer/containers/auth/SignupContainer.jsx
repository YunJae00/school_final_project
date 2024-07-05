import React, { useState } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { executeCheckEmailService, executeRegisterMemberService } from "../../../../api/ApiService";
import Swal from 'sweetalert2';

const SignupContainerStyled = styled.div`
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

const SignupContainer = ({ onLoginClick }) => {
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [nickname, setNickname] = useState("");
    const [error, setError] = useState(false);
    const [passwordMatchError, setPasswordMatchError] = useState(false);
    const [emailInUseError, setEmailInUseError] = useState(false);

    async function signupMember() {
        const member = {
            email: email,
            password: password,
            nickName: nickname
        };
        await executeRegisterMemberService(member)
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: '회원가입이 완료되었습니다.',
                    showConfirmButton: false,
                    timer: 3000
                });
                resetForm();
                onLoginClick();
            })
            .catch(error => console.log(error));
    }

    const handleSignupClick = async () => {
        if (!email || !password || !confirmPassword || !nickname) {
            setError(true);
            setPasswordMatchError(false);
            setEmailInUseError(false);
        } else if (password !== confirmPassword) {
            setError(false);
            setPasswordMatchError(true);
            setEmailInUseError(false);
        } else {
            setError(false);
            setPasswordMatchError(false);

            try {
                const response = await executeCheckEmailService(email);
                if (response.data) {
                    setEmailInUseError(true);
                } else {
                    setEmailInUseError(false);
                    await signupMember();
                }
            } catch (error) {
                console.error("Email check failed", error);
            }
        }
    };

    const resetForm = () => {
        setEmail("");
        setPassword("");
        setConfirmPassword("");
        setNickname("");
        setError(false);
        setPasswordMatchError(false);
        setEmailInUseError(false);
    };

    return (
        <SignupContainerStyled>
            <p style={{ color: "black", fontWeight: "bold", fontSize: "30px", marginBottom: "20px" }}>회원가입</p>
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
                <InputLabel>이름</InputLabel>
                <Input
                    type="text"
                    placeholder="이름을 입력하세요"
                    value={nickname}
                    onChange={(e) => setNickname(e.target.value)}
                    error={error && !nickname}
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
            <InputContainer>
                <InputLabel>비밀번호 확인</InputLabel>
                <Input
                    type="password"
                    placeholder="비밀번호를 재입력하세요"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    error={error && (!confirmPassword || password !== confirmPassword)}
                />
            </InputContainer>
            {error && <ErrorMessage>모든 필드를 올바르게 입력해주세요.</ErrorMessage>}
            {passwordMatchError && <ErrorMessage>비밀번호가 일치하지 않습니다.</ErrorMessage>}
            {emailInUseError && <ErrorMessage>이미 사용 중인 이메일입니다.</ErrorMessage>}
            <Button onClick={handleSignupClick}>회원가입</Button>
            <LinkText onClick={onLoginClick}>로그인 페이지로 돌아가기</LinkText>
        </SignupContainerStyled>
    );
};

export default SignupContainer;
