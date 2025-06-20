package com.example.teamproject_qna.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.teamproject_qna.dto.LoginRequestDTO;
import com.example.teamproject_qna.dto.LoginResponseDTO;
import com.example.teamproject_qna.dto.TokenRefreshRequestDTO;
import com.example.teamproject_qna.dto.TokenRefreshResponseDTO;
import com.example.teamproject_qna.dto.UserDTO;
import com.example.teamproject_qna.entity.Member;
import com.example.teamproject_qna.repository.MemberRepository;
import com.example.teamproject_qna.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public AuthService(MemberRepository memberRepository, JWTUtil jwtUtil) {
    	this.memberRepository = memberRepository;
    	this.jwtUtil = jwtUtil;
    	this.passwordEncoder = new BCryptPasswordEncoder();
    }
    

    // 로그인
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
    	// 1. 사용자 조회
        Member member = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        
        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        
        // 3. UserDTO 생성
        UserDTO userDTO = UserDTO.builder()
                .mid(member.getMid())
                .username(member.getUsername())
                .name(member.getName())
                .role(member.getRole())
                .job(member.getJob())
                .build();
        
        // 4. 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(userDTO);
        String refreshToken = jwtUtil.generateRefreshToken(userDTO);
        
        // 5. 응답 생성
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(member.getUsername())
                .name(member.getName())
                .role(member.getRole())
                .job(member.getJob())
                .build();
    }
    
    
    // 토큰 갱신
    public TokenRefreshResponseDTO refreshToken(TokenRefreshRequestDTO refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();
        
        // 1. Refresh Token 유효성 검증
        if (!jwtUtil.isValid(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 Refresh Token입니다.");
        }
        
        // 2. 토큰에서 사용자 정보 추출
        UserDTO userDTO = jwtUtil.getUserFromToken(refreshToken);
        
        // 3. 사용자 존재 확인 (선택적)
        if (!memberRepository.existsById(userDTO.getMid())) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        
        // 4. 새 토큰 생성
        String newAccessToken = jwtUtil.generateAccessToken(userDTO);
        String newRefreshToken = jwtUtil.generateRefreshToken(userDTO);
        
        return TokenRefreshResponseDTO.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
    
}
