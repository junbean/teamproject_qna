package com.example.teamproject_qna.filter;

import java.awt.List;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.teamproject_qna.dto.UserDTO;
import com.example.teamproject_qna.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;



/**
 * JWT 인증 및 권한 검증 필터
 * - FilterConfig에서 지정된 보호 경로에만 적용됨
 * - 토큰 유효성 검증 후 사용자 권한에 따른 접근 제어
 */
@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
	 * 요청이 들어올 때마다 실행되는 필터 메소드
	 * FilterConfig에서 지정한 보호 경로에만 적용됨
	 */
	@Override
	protected void doFilterInternal(
		HttpServletRequest request, 
		HttpServletResponse response, 
		FilterChain filterChain
	) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
        log.debug("JWT 필터 실행 - 요청 경로: {}", requestURI);
        
        // 1. Authorization 헤더에서 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");
        
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            log.debug("토큰이 없음 - 401 응답");
            sendErrorResponse(response, 401, "인증이 필요합니다. 토큰을 제공해주세요.");
            return;
        }
        
        // 2. 토큰 추출
        String token = authorizationHeader.substring(7); // "Bearer " 제거
        
        
        try {
        	// 3. 토큰 유효성 검증 (만료, 서명 등)
            if (!jwtUtil.isValid(token)) {
                log.debug("유효하지 않은 토큰 - 401 응답");
                sendErrorResponse(response, 401, "유효하지 않은 토큰입니다.");
                return;
            }
            
            // 4. Access Token인지 확인 (Refresh Token 접근 방지)
            if (!jwtUtil.isAccessToken(token)) {
                log.debug("Access Token이 아님 - 401 응답");
                sendErrorResponse(response, 401, "올바른 Access Token을 사용해주세요.");
                return;
            }
            
            // 5. 토큰에서 사용자 정보 추출
            UserDTO userDTO = jwtUtil.getUserFromToken(token);
            log.debug("토큰에서 사용자 정보 추출 성공: {} ({})", userDTO.getUsername(), userDTO.getRole());
            
            // 6. 경로별 권한 확인
            if (!hasPermission(requestURI, userDTO.getRole())) {
                log.warn("권한 없음 - 사용자: {}, 역할: {}, 요청 경로: {}", userDTO.getUsername(), userDTO.getRole(), requestURI);
                sendErrorResponse(response, 403, "해당 페이지에 접근할 권한이 없습니다.");
                return;
            }
            
            // 7. 사용자 정보를 request에 저장 (컨트롤러에서 사용하기 위해)
            request.setAttribute("user", userDTO);
            
            log.debug("인증 성공: {} ({})", userDTO.getUsername(), userDTO.getRole());
            
        } catch (Exception e) {
        	log.error("JWT 처리 중 오류 발생: {}", e.getMessage(), e);
            sendErrorResponse(response, 401, "토큰 처리 중 오류가 발생했습니다.");
            return;
        }

        // 8. 인증 및 권한 확인이 완료되면 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
	}
	
	
	/**
     * 경로별 권한 확인 메소드
     * @param path 요청 경로
     * @param role 사용자 역할 (USER 또는 MANAGER)
     * @return 접근 권한 여부
     */
    private boolean hasPermission(String path, String role) {
        // /admin으로 시작하는 경로는 MANAGER만 접근 가능
        if (path.startsWith("/admin")) {
            log.debug("관리자 페이지 접근 시도 - 사용자 역할: {}", role);
            return "MANAGER".equals(role);
        }
        
        // /api/admin으로 시작하는 API는 MANAGER만 접근 가능
        if (path.startsWith("/api/admin")) {
            log.debug("관리자 API 접근 시도 - 사용자 역할: {}", role);
            return "MANAGER".equals(role);
        }
        
        // 기타 보호된 경로는 인증된 모든 사용자 접근 가능 (USER, MANAGER 모두)
        log.debug("일반 보호 경로 접근 - 사용자 역할: {}", role);
        return "USER".equals(role) || "MANAGER".equals(role);
    }
    
    /**
     * 에러 응답을 JSON 형태로 전송
     * @param response HTTP 응답 객체
     * @param status HTTP 상태 코드 (401, 403 등)
     * @param message 에러 메시지
     */
    private void sendErrorResponse(HttpServletResponse response, int status, String message) 
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        
        // JSON 형태의 에러 응답 생성
        String jsonResponse = String.format(
            "{\"error\":\"%s\", \"status\":%d, \"timestamp\":\"%s\"}", 
            message, status, java.time.LocalDateTime.now().toString()
        );
        
        response.getWriter().write(jsonResponse);
        log.debug("에러 응답 전송 - 상태코드: {}, 메시지: {}", status, message);
    }
	
}


/*
// 에러 응답 전송
private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
    response.setStatus(status);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"error\":\"" + message + "\"}");
}
*/
