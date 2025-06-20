package com.example.teamproject_qna.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.teamproject_qna.entity.AnswerEntity;
import com.example.teamproject_qna.repository.AdminAnswerRepository;

@Service
public class AdminAnswerService {

	@Autowired
	private AdminAnswerRepository adminAnswerRepository;
	
	@Autowired
	private AdminBoardService adminBoardService;

	// 답변 등록
	public void save(AnswerEntity answerEntity) {
		adminAnswerRepository.save(answerEntity);
		
	}
	
	// transaction 이용하여 답변 등록 이후 Board 테이블의 isAnswered 컬럼 "Y"로 수정.
	@Transactional
    public void registerAnswerAndUpdateBoard(AnswerEntity answerEntity,
    										 Long bid) {
        adminAnswerRepository.save(answerEntity);     
        adminBoardService.updateQnaAnswer(bid);
    }
	
	
	
}
