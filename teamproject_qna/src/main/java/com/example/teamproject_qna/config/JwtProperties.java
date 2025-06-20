package com.example.teamproject_qna.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

//4. JWT 설정 클래스
@Component
@Getter
public class JwtProperties {

    @Value("${spring.jwt.secret}")
    private String secret;
    
    @Value("${spring.jwt.access-token-expiration}")
    private long accessTokenExpiration;
    
    @Value("${spring.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    
    // 토큰 타입
    public static final String ACCESS_TOKEN_TYPE = "ACCESS";
    public static final String REFRESH_TOKEN_TYPE = "REFRESH";
    
    // 헤더명
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
}
