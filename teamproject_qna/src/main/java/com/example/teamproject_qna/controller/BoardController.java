package com.example.teamproject_qna.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.entity.Member;
import com.example.teamproject_qna.repository.MemberRepository;
import com.example.teamproject_qna.service.BoardService;
import com.example.teamproject_qna.util.JWTUtil;

import java.util.HashMap;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

    // 문의글 폼 이동
    @GetMapping("/board")
    public String board() {
        return "board";
    }

    // 문의글 등록 처리
    @PostMapping("/qnaProc")
    public String qnaProc(
        @RequestParam("category") String category,
        @RequestParam("title") String title,
        @RequestParam("content") String content,
        HttpServletRequest request
    ) {
        // JWT 토큰 인증
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

        // 게시글 생성 및 저장
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setCategory(category);
        boardEntity.setTitle(title);
        boardEntity.setContent(content);
        boardEntity.setWriter(member);
        boardEntity.setIsAnswered("답변 대기");

        boardService.boardInsert(boardEntity);

        return "redirect:/mypage";
    }


    // 🔄 HTML 뷰만 반환 (데이터는 JS fetch로 따로 요청)
    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    // ✅ JSON API - 로그인한 사용자의 게시글 목록 반환 (JWT 헤더 필요)
    @GetMapping("/api/mypage")
    @ResponseBody
    public ResponseEntity<?> getMyBoards(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT 토큰이 없습니다.");
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.getUsername(token);
        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자를 찾을 수 없습니다.");
        }

        List<BoardEntity> boardList = boardService.getBoardsByWriter(member);

        // 필요한 정보만 추출해서 JSON으로 응답
        List<Map<String, Object>> result = boardList.stream().map(board -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", board.getBid());
            map.put("title", board.getTitle());
            map.put("category", board.getCategory());
            map.put("username", board.getWriter().getUsername());
            map.put("name", board.getWriter().getName());
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    // 게시글 상세 보기
    @GetMapping("/board/detail/{id}")
    public String boardDetail(@PathVariable("id") Long id, Model model) {
        BoardEntity board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "boardDetail";
    }
}