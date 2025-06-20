package com.example.teamproject_qna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.teamproject_qna.dto.AnswerDTO;
import com.example.teamproject_qna.entity.AnswerEntity;
import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.service.AdminAnswerService;
import com.example.teamproject_qna.service.AdminBoardService;
import com.example.teamproject_qna.service.Conversion;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminBoardService adminBoardService;
	
	@Autowired
	private AdminAnswerService adminAnswerService;
	
	@Autowired
	private Conversion conversion;
	@GetMapping("/")
	public String root() {
		return "admin/index";
	}
	
	@GetMapping("/showAllQna")
	public String showAllQna(Model model) {
		List<BoardEntity> bList = adminBoardService.showAllQna();
		model.addAttribute("bList", bList);
		return "admin/showAllQna";
	}
	
	@GetMapping("/showMyQna")
	public String showMyQna(Model model) {
	
		// 접속한 매니저의 직무 확인 필요 
		
		
		List<BoardEntity> bList = adminBoardService.showNeedAnswer();
		
		
		model.addAttribute("bList", bList);
		return "admin/showNeedAnswer";
	}
	
	@GetMapping("/showDetailQna/{title}")
	public String showDetailQna(@PathVariable("title")String title,
								Model model){
		BoardEntity board = adminBoardService.findByTitle(title);
		model.addAttribute("board", board);
		
		return "admin/showNeedAnswerDetail";
	}
	
	@PostMapping("/registAnswer")
	public String registAnswer(@ModelAttribute AnswerDTO answerDTO) {
		AnswerEntity answerEntity= conversion.toEntity(answerDTO); // DTO -> Entity 컨버전
		
//		adminAnswerService.save(answerEntity);  // Transactional 처리 위함.
//		adminBoardService.updateQnaAnswer(answerDTO.getBid()); // Transactional 처리 위함.
		
		adminAnswerService.registerAnswerAndUpdateBoard(answerEntity,answerDTO.getBid());
		
		return "redirect:/admin/showMyQna";
	}
	
}
