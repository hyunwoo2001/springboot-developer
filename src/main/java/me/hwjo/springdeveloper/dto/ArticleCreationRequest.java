package me.hwjo.springdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.hwjo.springdeveloper.dao.Article;

@Getter
@AllArgsConstructor
public class ArticleCreationRequest {
    private String title;
    private String content;

    public me.hwjo.springdeveloper.dto.Article toEntity() {
        return Article.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}