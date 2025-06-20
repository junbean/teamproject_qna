package com.example.teamproject_qna.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.teamproject_qna.dto.UserDTO;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    private final SecretKey secretKey;  // 한 번만 생성해서 재사용
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
    
    // 생성자에서 SecretKey와 설정값들을 미리 생성
    public JWTUtil(@Value("${spring.jwt.secret}") String secret,
                   @Value("${spring.jwt.access-token-expiration}") long accessTokenExpiration,
                   @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
    
 // Access Token 생성
    public String generateAccessToken(UserDTO userDTO) {
        return generateToken(userDTO, accessTokenExpiration, "ACCESS");
    }
    
    // Refresh Token 생성
    public String generateRefreshToken(UserDTO userDTO) {
        return generateToken(userDTO, refreshTokenExpiration, "REFRESH");
    }
    
    // 토큰 생성 공통 메서드
    private String generateToken(UserDTO userDTO, long expiration, String tokenType) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
                .claim("mid", userDTO.getMid()) // mid를 subject로 사용
                .claim("username", userDTO.getUsername())
                .claim("name", userDTO.getName())
                .claim("role", userDTO.getRole())
                .claim("job", userDTO.getJob())
                .claim("tokenType", tokenType) // 토큰 타입 구분
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }
    
    // 토큰에서 username 추출
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }
    
    // 토큰 유효성 검사
    public boolean isValid(String token) {
        try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parse(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    
}
