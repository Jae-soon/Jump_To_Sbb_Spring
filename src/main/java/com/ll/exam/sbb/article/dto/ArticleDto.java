package com.ll.exam.sbb.article.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ArticleDto {
    private static int lastId = 0;
    private int id;
    private String title;
    private String body;

    public ArticleDto(String title, String body) {
        this(++lastId, title, body);
    }
}
