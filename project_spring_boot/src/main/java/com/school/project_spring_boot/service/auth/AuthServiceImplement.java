package com.school.project_spring_boot.service;

import com.school.project_spring_boot.dto.JwtTokenDto;
import com.school.project_spring_boot.dto.LoginRequestDto;
import com.school.project_spring_boot.dto.MemberRequestDto;
import com.school.project_spring_boot.dto.MemberResponseDto;
import com.school.project_spring_boot.entity.Member;
import com.school.project_spring_boot.provider.JwtUtil;
import com.school.project_spring_boot.repository.MemberRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImplement {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImplement(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public MemberResponseDto signUp(MemberRequestDto memberRequestDto) {
        Member member = memberRequestDto.toMember(bCryptPasswordEncoder);
        return MemberResponseDto.from(memberRepository.save(member));
    }

    public JwtTokenDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Optional<Member> memberOpt = memberRepository.findByEmail(email);
        if (memberOpt.isPresent()){
            Member member = memberOpt.get();
            if(member == null) {
                throw new UsernameNotFoundException("not found by email");
            }
            if(!bCryptPasswordEncoder.matches(password, member.getPassword())) {
                throw new BadCredentialsException("bad credentials");
            }

            return jwtUtil.createJwtToken(member);
        }
        else return null;
    }
}
