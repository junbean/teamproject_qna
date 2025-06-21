package com.example.teamproject_qna.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BoardDTO {

    private String title;
    private String content;
    private String category;
}
