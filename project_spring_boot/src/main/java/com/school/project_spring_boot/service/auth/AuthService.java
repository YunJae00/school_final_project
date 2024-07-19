package com.school.project_spring_boot.service.auth;

import com.school.project_spring_boot.dto.requset.auth.*;
import com.school.project_spring_boot.dto.response.auth.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto emailCheckRequestDto);
    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto emailCertificationRequestDto);
    ResponseEntity<? super CertificationCheckResponseDto> certificationCheck(CertificationCheckRequestDto certificationCheckRequestDto);
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto signInRequestDto);
}
