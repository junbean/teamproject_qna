package com.example.teamproject_qna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.entity.Member;
import com.example.teamproject_qna.repository.MemberRepository;
import com.example.teamproject_qna.service.BoardService;
import com.example.teamproject_qna.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private MemberRepository memberRepository;

	// board 폼 이동
	@GetMapping("/board")
	public String board() {
		return "board";
	}

	// board 등록 처리
	@PostMapping("/qnaProc")
	public String qnaProc(@RequestHeader("Authorization") String authHeader, BoardEntity boardEntity) {
		// 1. JWT 토큰에서 유저 이름 추출
		String token = authHeader.replace("Bearer ", "");
		String username = jwtUtil.getUsername(token);

		// 2. username으로 Member 객체 조회
		Member member = memberRepository.findByUsername(username);
		
		// 3. 게시글 작성자 설정
		boardEntity.setWriter(member);

		// 3. 게시글 저장
		boardService.boardInsert(boardEntity);

		return "redirect:/mypage";
	}
	
	@GetMapping("/mypage")
	public String myBoardList(HttpServletRequest request, Model model) {
	    // 1. Authorization 헤더에서 토큰 꺼내기
	    String authHeader = request.getHeader("Authorization");
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new RuntimeException("JWT 토큰이 없습니다.");
	    }

	    String token = authHeader.substring(7); // "Bearer " 이후

	    // 2. JWT 토큰에서 사용자 이름 추출
	    String username = jwtUtil.getUsername(token);

	    // 3. 사용자 정보 가져오기
	    Member loginUser = memberRepository.findByUsername(username);

	    // 4. 해당 사용자가 작성한 글만 조회
	    List<BoardEntity> boardList = boardService.getBoardsByWriter(loginUser);

	    model.addAttribute("boardList", boardList);
	    return "mypage";
	}

}
