package com.example.teamproject_qna.dto;

import com.example.teamproject_qna.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnswerDTO {
    private Long aid;
    private String content;
    private Long writerid;
    private Long bid;
}
