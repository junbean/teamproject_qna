package com.example.teamproject_qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//로그인 응답 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String name;
    private String role;
    private String job;
}
