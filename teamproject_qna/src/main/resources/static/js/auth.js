// 일반 사용자 페이지에서 사용할 인증 스크립트
class AuthManager {
    static checkAuth() {
        const token = localStorage.getItem('accessToken');
        
        if (!token) {
            this.redirectToLogin();
            return false;
        }
        
        // 간단한 토큰 유효성 검증
        return this.validateToken(token);
    }
    
    static validateToken(token) {
        try {
            // JWT 토큰 파싱 (간단 검증)
            const payload = JSON.parse(atob(token.split('.')[1]));
            const now = Date.now() / 1000;
            
            if (payload.exp < now) {
                this.redirectToLogin();
                return false;
            }
            
            return true;
        } catch (e) {
            this.redirectToLogin();
            return false;
        }
    }
    
    static redirectToLogin() {
        const currentUrl = window.location.pathname + window.location.search;
        window.location.href = `/login?redirect=${encodeURIComponent(currentUrl)}`;
    }
}

// 보호 페이지에서 자동 실행
document.addEventListener('DOMContentLoaded', function() {
    if (document.body.classList.contains('auth-required')) {
        AuthManager.checkAuth();
    }
});