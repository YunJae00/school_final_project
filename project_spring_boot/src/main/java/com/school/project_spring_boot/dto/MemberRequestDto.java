package com.school.project_spring_boot.dto;

import com.school.project_spring_boot.entity.Member;
import com.school.project_spring_boot.entity.Provider;
import com.school.project_spring_boot.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MemberRequestDto {

    private final String email;
    private final String password;
    private final String nickName;
    private final String provider;
    private final String role;

    public MemberRequestDto(String email, String password, String nickName, String provider, String role) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.provider = provider;
        this.role = role;
    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(passwordEncoder.encode(password));
        member.setNickName(nickName);
        member.setProvider(Provider.Local);
        member.setRole(Role.User);
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        return member;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProvider() {
        return provider;
    }

    public String getRole() {
        return role;
    }
}
