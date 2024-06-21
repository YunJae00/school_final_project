package com.school.project_spring_boot.service;

import com.school.project_spring_boot.dto.JwtTokenDto;
import com.school.project_spring_boot.dto.MemberRequestDto;
import com.school.project_spring_boot.dto.MemberResponseDto;
import com.school.project_spring_boot.entity.Member;
import com.school.project_spring_boot.jwt.JwtProvider;
import com.school.project_spring_boot.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public MemberResponseDto signUp(MemberRequestDto memberRequestDto) {
        Member member = memberRequestDto.toMember(bCryptPasswordEncoder);
        return MemberResponseDto.from(memberRepository.save(member));
    }

    public JwtTokenDto login(MemberRequestDto memberRequestDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberRequestDto.getEmail(), memberRequestDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return jwtProvider.createJwtToken(authentication);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
