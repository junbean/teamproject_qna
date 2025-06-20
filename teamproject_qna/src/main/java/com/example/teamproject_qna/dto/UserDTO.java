package com.example.teamproject_qna.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	private Long mid;
	private String username;
	private String name;
	private String role;
	private String job;
	    
	    // 권한 체크 편의 메서드
	public boolean isManager() {
	    return "MANAGER".equals(role);
	}
	
	public boolean isUser() {
	    return "USER".equals(role);
	}
	
	public boolean hasJob(String jobName) {
	    return jobName != null && jobName.equals(job);
	}
}
