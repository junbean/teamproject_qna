package com.example.teamproject_qna.repository;



import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByUsername(String username);

	boolean existsByUsername(String username);
	
	
}
