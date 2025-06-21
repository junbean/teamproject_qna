package com.example.teamproject_qna.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.repository.AdminBoardRepository;

@Service
public class AdminBoardService {

	@Autowired
	private AdminBoardRepository adminBoardRepository;

	// 모든 Qna 리스트 가져오기
	public List<BoardEntity> showAllQna() {
		List<BoardEntity> bList = adminBoardRepository.findAll();
		return bList ;
		
	}

	// isAnswered 가 "N"인 리스트 가져오기
	public List<BoardEntity> showNeedAnswer() {
		List<BoardEntity> bList = adminBoardRepository.findByIsAnswered("N");
		return bList;
	}

	// 상세보기 페이지
	public BoardEntity findByTitle(String title) {
		BoardEntity board = adminBoardRepository.findByTitle(title);
		return board;
	}
	
	
	// 답변 등록 후 IsAnswered 컬럼 "Y"로 업데이트
	public void updateQnaAnswer(Long bid) {
		Optional<BoardEntity> optionalBoard = adminBoardRepository.findById(bid);
		   
		 if (optionalBoard.isPresent()) {
		        BoardEntity board = optionalBoard.get();
		        board.setIsAnswered("Y");
		        
		        adminBoardRepository.save(board);
		    } else {
		        System.out.println("해당 게시글이 존재하지 않습니다.");
		    }
	}

	
	
	
	
}
