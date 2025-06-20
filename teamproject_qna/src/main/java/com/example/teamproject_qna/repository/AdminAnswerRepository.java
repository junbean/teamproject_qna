package com.example.teamproject_qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.AnswerEntity;

public interface AdminAnswerRepository extends JpaRepository<AnswerEntity,Long>{


}
