package com.example.teamproject_qna.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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

	// qnaPage 폼 이동
	@GetMapping("/board")
	public String board() {
		return "board";
	}

	// qna 등록 처리
	@PostMapping("/qnaProc")
	public String qnaProc(
	        HttpServletRequest request,
	        @RequestBody Map<String, String> payload // JSON 객체 받기
	) {
	    String category = payload.get("category");
	    String title = payload.get("title");
	    String content = payload.get("content");

	    System.out.println("카테고리: " + category);
	    System.out.println("제목: " + title);
	    System.out.println("내용: " + content);

	    String authHeader = request.getHeader("Authorization");
	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT 토큰이 유효하지 않거나 누락되었습니다.");
	    }

	    String token = authHeader.substring(7);
	    String username = jwtUtil.getUsername(token);
	    Member member = memberRepository.findByUsername(username);
	    if (member == null) {
	        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유저를 찾을 수 없습니다.");
	    }

	    BoardEntity boardEntity = new BoardEntity();
	    boardEntity.setCategory(category);
	    boardEntity.setTitle(title);
	    boardEntity.setContent(content);
	    boardEntity.setWriter(member);

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
	
	@GetMapping("/board/detail/{id}")
	public String boardDetail(@PathVariable("id") Long id, Model model) {
	    BoardEntity board = boardService.getBoardById(id);
	    model.addAttribute("board", board);
	    return "boardDetail";
	}


}
