package com.example.teamproject_qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String root() {
		return "index";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/regist")
	public String registForm() {
		return "registForm";
	}
	
	@GetMapping("/mypage")
	public String myPage() {
		return "mypage";
	}
}
