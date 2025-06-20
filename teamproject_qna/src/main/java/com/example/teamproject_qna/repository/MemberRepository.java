package com.example.teamproject_qna.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);
	
	boolean existByUsername(String username);
	
	Optional<Member> findByRoleAndJob(String role, String job);
}
