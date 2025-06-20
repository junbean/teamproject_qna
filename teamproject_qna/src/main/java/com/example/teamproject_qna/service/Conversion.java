package com.example.teamproject_qna.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.teamproject_qna.dto.AnswerDTO;
import com.example.teamproject_qna.entity.AnswerEntity;
import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.entity.Member;
import com.example.teamproject_qna.repository.AdminBoardRepository;
import com.example.teamproject_qna.repository.MemberRepository;

@Service
public class Conversion {

	@Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdminBoardRepository boardRepository;

	
	
    public AnswerEntity toEntity(AnswerDTO dto) {

        Member writer = null;
        BoardEntity board = null;

        // writerId로 Member 객체 조회
        if (dto.getWriterid() != null) {
            Optional<Member> memberOpt = memberRepository.findById(dto.getWriterid());
            if (memberOpt.isPresent()) {
                writer = memberOpt.get();
            } else {
                throw new IllegalArgumentException("해당 작성자 없음");
            }
        }

        // boardId로 BoardEntity 객체 조회
        if (dto.getBid() != null) {
            Optional<BoardEntity> boardOpt = boardRepository.findById(dto.getBid());
            if (boardOpt.isPresent()) {
                board = boardOpt.get();
            } else {
                throw new IllegalArgumentException("해당 게시글 없음");
            }
        }

        // Entity 생성
        AnswerEntity entity = new AnswerEntity();
        entity.setContent(dto.getContent());
        entity.setWriter(writer);
        entity.setBoardEntity(board);

        return entity;
    }
}
