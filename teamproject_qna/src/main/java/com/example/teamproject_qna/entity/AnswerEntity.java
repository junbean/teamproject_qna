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
@Table(name = "answer98")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerEntity {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long aid;
	 
	    @Column(nullable = false, length = 1000)
	    private String content;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "writer")
	    private Member writer;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "board_id", nullable = false)
	    private BoardEntity boardEntity;
}
