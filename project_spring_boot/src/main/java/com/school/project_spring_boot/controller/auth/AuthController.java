package com.school.project_spring_boot.controller.auth;

import com.school.project_spring_boot.dto.requset.auth.*;
import com.school.project_spring_boot.dto.response.auth.*;
import com.school.project_spring_boot.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/check-email")
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(
            @RequestBody @Valid EmailCheckRequestDto requestBody
    ){
        ResponseEntity<? super EmailCheckResponseDto> response  = authService.emailCheck(requestBody);
        return response;
    }

    @PostMapping("/send-certification-email")
    public ResponseEntity<? super EmailCertificationResponseDto> sendCertificationEmail(
            @RequestBody @Valid EmailCertificationRequestDto requestBody
    ){
        ResponseEntity<? super EmailCertificationResponseDto> response = authService.emailCertification(requestBody);
        return response;
    }

    @PostMapping("/check-certification-number")
    public ResponseEntity<? super CertificationCheckResponseDto> checkCertificationNumber(
            @RequestBody @Valid CertificationCheckRequestDto requestBody
    ){
        ResponseEntity<? super CertificationCheckResponseDto> response = authService.certificationCheck(requestBody);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(
            @RequestBody @Valid SignUpRequestDto requestBody
    ){
        ResponseEntity<? super SignUpResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
            @RequestBody @Valid SignInRequestDto requestBody
    ){
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
}
