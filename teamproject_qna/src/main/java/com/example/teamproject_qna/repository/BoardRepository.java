package com.example.teamproject_qna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.BoardEntity;
import com.example.teamproject_qna.entity.Member;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	List<BoardEntity> findByWriter(Member loginUser);

}
