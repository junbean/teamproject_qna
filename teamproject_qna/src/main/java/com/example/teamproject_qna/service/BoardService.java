package com.example.teamproject_qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.entity.Member;
import com.example.teamproject_qna.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	public BoardEntity boardInsert(BoardEntity boardEntity) {
		return boardRepository.save(boardEntity);
	}

	public List<BoardEntity> getAllBoards() {
		
		return boardRepository.findAll();
	}

	public List<BoardEntity> getBoardsByWriter(Member loginUser) {
		  return boardRepository.findByWriter(loginUser);
	}

}
