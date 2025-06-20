package com.example.teamproject_qna.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.teamproject_qna.dto.RegisterRequestDTO;
import com.example.teamproject_qna.entity.Member;
import com.example.teamproject_qna.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder; 
    
    public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
    	this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder; 
    }
    
    // 회원가입
    public void register(RegisterRequestDTO request) {
        // 1. 중복 사용자명 체크
        if (memberRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자명입니다.");
        }
        
        // 2. 회원 생성
        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role("USER")
                .build();
        
        // 4. 저장
        memberRepository.save(member);
        log.info("새 회원 가입: {}", request.getUsername());
    }
    
    // 비밀번호 암호화 (회원가입용)
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
    
    // 사용자명 중복 체크
    public boolean isUsernameExists(String username) {
        return memberRepository.existsByUsername(username);
    }
}
