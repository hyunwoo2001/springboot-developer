package me.hwjo.dto;

import lombok.Getter;
import lombok.Setter;
import me.hwjo.dao.Article;

@Getter
@Setter
public class ArticleCreationRequest {

    private String title;
    private String content;

    public ArticleCreationRequest(String 제목, String 내용) {
    }

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}