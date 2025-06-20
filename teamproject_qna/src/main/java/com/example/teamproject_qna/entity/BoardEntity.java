package com.example.teamproject_qna.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board98")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bid;

	@Column(nullable = false, length = 200)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer", nullable = false)
	private Member writer; // writer는 Member 객체로 매핑

	@Column(nullable = false, length = 20)
	private String category;
	
	@Column(nullable = false, length = 20, name="isanswered")
	private String isAnswered;


	
}