package com.example.teamproject_qna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.BoardEntity;

public interface AdminBoardRepository extends JpaRepository<BoardEntity,Long>{

	List<BoardEntity> findByIsAnswered(String isAnswered);

	BoardEntity findByTitle(String title);


}
