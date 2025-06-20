package com.example.teamproject_qna.controller;

import java.util.HashMap;
import java.util.Map;

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
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody RegisterRequestDTO request) {
    	try {
            memberService.register(request);
            
            // JSON 응답으로 변경
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "회원가입이 완료되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            
            // 실패 시에도 JSON으로 응답
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "회원가입 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // 사용자명 중복 체크
    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
    	try {
            boolean exists = memberService.isUsernameExists(username);
            
            Map<String, Object> response = new HashMap<>();
            response.put("exists", exists);
            
            if (exists) {
                response.put("message", "이미 존재하는 사용자명입니다.");
                return ResponseEntity.badRequest().body(response);
            } else {
                response.put("message", "사용 가능한 사용자명입니다.");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            log.error("사용자명 체크 실패: {}", e.getMessage());
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "사용자명 체크 실패: " + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
