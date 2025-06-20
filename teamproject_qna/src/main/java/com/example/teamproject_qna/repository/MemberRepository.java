package com.example.teamproject_qna.repository;

<<<<<<< HEAD
=======

import java.util.Optional;

>>>>>>> dev
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teamproject_qna.entity.Member;

<<<<<<< HEAD
public interface MemberRepository extends JpaRepository<Member, Integer>{

	Member findByUsername(String username);

=======
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUsername(String username);

	boolean existsByUsername(String username);
	
	Optional<Member> findByRoleAndJob(String role, String job);
>>>>>>> dev
}
