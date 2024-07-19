package com.school.project_spring_boot.entity.auth;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.school.project_spring_boot.dto.requset.auth.SignUpRequestDto;
import com.school.project_spring_boot.entity.MemberFavoriteStock;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="members")
@Getter
@Setter
public class Member {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MemberFavoriteStock> favoriteStocks = new ArrayList<>();

    public Member() {
    }

    public Member(Long id, String email, String password, String nickName, Provider provider, String providerId, Role role, String refreshToken, LocalDateTime createdAt, LocalDateTime updatedAt, List<MemberFavoriteStock> favoriteStocks) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favoriteStocks = favoriteStocks;
    }

    public Member(SignUpRequestDto dto){
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickName = dto.getNickName();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.role = Role.valueOf(dto.getRole());
        this.provider = Provider.Local;
        this.providerId = null;
        this.refreshToken = null;
    }
}