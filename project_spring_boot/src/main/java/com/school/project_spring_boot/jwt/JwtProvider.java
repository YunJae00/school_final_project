package com.school.project_spring_boot.jwt;

import com.school.project_spring_boot.dto.JwtTokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final String BEARER_TYPE = "Bearer";
    private final SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7일
    private final String issuer;

    public JwtProvider(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.issuer}") String issuer
    )
    {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        this.issuer = issuer;
    }

    public JwtTokenDto createJwtToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken =
                Jwts.builder()
                        .setSubject(authentication.getName())
                        .claim("role", authorities)
                        .setExpiration(accessTokenExpiresIn)
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();

        String refreshToken =
                Jwts.builder()
                        .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();

        return new JwtTokenDto(BEARER_TYPE, accessToken, refreshToken, accessTokenExpiresIn.getTime());
    }
}
