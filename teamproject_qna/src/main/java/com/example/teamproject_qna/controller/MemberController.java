package com.example.teamproject_qna.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamproject_qna.dto.RegisterRequestDTO;
import com.example.teamproject_qna.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
    	this.memberService = memberService;
    }
    
    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            memberService.register(request);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("회원가입 실패: " + e.getMessage());
        }
    }
    
    // 사용자명 중복 체크
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
        try {
            boolean exists = memberService.isUsernameExists(username);
            if (exists) {
                return ResponseEntity.badRequest().body("이미 존재하는 사용자명입니다.");
            } else {
                return ResponseEntity.ok("사용 가능한 사용자명입니다.");
            }
        } catch (Exception e) {
            log.error("사용자명 체크 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("사용자명 체크 실패: " + e.getMessage());
        }
    }
}
