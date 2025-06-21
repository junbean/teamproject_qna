package com.example.teamproject_qna.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teamproject_qna.dto.LoginRequestDTO;
import com.example.teamproject_qna.dto.LoginResponseDTO;
import com.example.teamproject_qna.dto.TokenRefreshRequestDTO;
import com.example.teamproject_qna.dto.TokenRefreshResponseDTO;
import com.example.teamproject_qna.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
		try {
			LoginResponseDTO response = authService.login(loginRequest);
			return ResponseEntity.ok().body(response);
		} catch(Exception e) {
            log.error("로그인 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("로그인 실패: " + e.getMessage());
		}
	}
	
	// 토큰 갱신
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequestDTO refreshRequest) {
        try {
            TokenRefreshResponseDTO response = authService.refreshToken(refreshRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("토큰 갱신 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().body("토큰 갱신 실패: " + e.getMessage());
        }
    }
    
    // 로그아웃 (클라이언트에서 토큰 삭제하도록 안내)
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("로그아웃되었습니다. 클라이언트에서 토큰을 삭제해주세요.");
    }
}
