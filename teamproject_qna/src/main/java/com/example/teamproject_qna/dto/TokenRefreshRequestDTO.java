package com.example.teamproject_qna.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//토큰 갱신 요청 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequestDTO {
    private String refreshToken;
}
