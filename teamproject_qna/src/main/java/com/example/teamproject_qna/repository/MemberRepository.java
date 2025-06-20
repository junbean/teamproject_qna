package com.example.teamproject_qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Member findByUsername(String username);

}
