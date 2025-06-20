package com.example.teamproject_qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//토큰 갱신 응답 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRefreshResponseDTO {
    private String accessToken;
    private String refreshToken;
}
