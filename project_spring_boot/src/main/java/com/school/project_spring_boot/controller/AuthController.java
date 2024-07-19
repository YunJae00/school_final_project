package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.JwtTokenDto;
import com.school.project_spring_boot.dto.LoginRequestDto;
import com.school.project_spring_boot.dto.MemberRequestDto;
import com.school.project_spring_boot.dto.MemberResponseDto;
import com.school.project_spring_boot.service.AuthServiceImplement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImplement authServiceImplement;

    public AuthController(AuthServiceImplement authServiceImplement) {
        this.authServiceImplement = authServiceImplement;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authServiceImplement.signUp(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        if((authServiceImplement.login(loginRequestDto)) == null){
            return ResponseEntity.ok(null);
        }
        else return ResponseEntity.ok(authServiceImplement.login(loginRequestDto));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
}
