package com.example.teamproject_qna.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.teamproject_qna.filter.JWTFilter;
import com.example.teamproject_qna.util.JWTUtil;

/**
 * 필터 설정 클래스
 * - JWT 필터를 특정 경로에만 적용하도록 설정
 * - 인증이 필요없는 공개 경로는 필터에서 제외
 */
@Configuration
public class FilterConfig {

	private final JWTFilter jwtFilter;

    public FilterConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    
    /**
     * JWT 필터를 서블릿 컨테이너에 등록
     * @return FilterRegistrationBean JWT 필터 등록 정보
     */
    @Bean
    FilterRegistrationBean<JWTFilter> jwtFilter() {
        FilterRegistrationBean<JWTFilter> filter = new FilterRegistrationBean<>();

        // 이미 @Component로 등록된 JWTFilter Bean 사용
        filter.setFilter(jwtFilter);
        
        // 인증이 필요한 경로만 지정 (나머지는 자동으로 필터가 적용되지 않음)
        filter.addUrlPatterns("/api/user/*");

        // 필터 실행 순서 설정 (숫자가 작을수록 먼저 실행)
        filter.setOrder(1);
        
        return filter;
    }
}

/*


// 인증이 필요한 경로만 지정 (나머지는 자동으로 필터가 적용되지 않음)
filter.addUrlPatterns(
    // === 웹 페이지 경로 ===
    "/mypage/*",        // 마이페이지 (로그인 필요)
    "/admin/*",         // 관리자 페이지 (MANAGER만 접근 가능)
    "/user/*",          // 일반 사용자 페이지
    "/board/*",         // 게시판 (로그인 필요한 기능들)
    "/qna/*",           // Q&A 관련 페이지
    
    // === API 경로 ===
    "/api/user/*",      // 사용자 관련 API
    "/api/admin/*",     // 관리자 전용 API (MANAGER만 접근 가능)
    "/api/mypage/*",    // 마이페이지 관련 API
    "/api/board/*",     // 게시판 관련 API
    "/api/qna/*"        // Q&A 관련 API
);
*/



/*
 * 필터가 적용되지 않는 공개 경로들:
 * - / (메인 페이지)
 * - /login (로그인 페이지)
 * - /regist (회원가입 페이지)
 * - /api/auth/login (로그인 API)
 * - /api/member/register (회원가입 API)
 * - /api/member/check-username (사용자명 중복 체크 API)
 * - /h2-console/** (H2 데이터베이스 콘솔)
 * - /favicon.ico, /css/**, /js/**, /images/** (정적 리소스)
 */


// === 웹 페이지 경로 ===
// "/mypage/*",        // 마이페이지 (로그인 필요)
// "/admin/*",         // 관리자 페이지 (MANAGER만 접근 가능)
// "/user/*",          // 일반 사용자 페이지
// "/board/*",         // 게시판 (로그인 필요한 기능들)
// "/qna/*"           // Q&A 관련 페이지

// === API 경로 ===
// "/api/user/*"      // 사용자 관련 API
//"/api/admin/*",     // 관리자 전용 API (MANAGER만 접근 가능)
//"/api/mypage/*"    // 마이페이지 관련 API
// "/api/board/*",     // 게시판 관련 API
// "/api/qna/*"        // Q&A 관련 API