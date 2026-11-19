package me.hwjo.dto;

import lombok.Getter;
import lombok.Setter;
import me.hwjo.domain.Article;

@Getter
@Setter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }
}