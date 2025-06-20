package com.example.teamproject_qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QnaController {

	
	// qnaPage 폼 이동
	@GetMapping("/qnaPage")
	public String qnaPage() {
		return"qnaPage";
	}
	
	// qnaPage 
	@PostMapping("/qnaProc")
	public String qnaProc() {
		return "";
	}
}
