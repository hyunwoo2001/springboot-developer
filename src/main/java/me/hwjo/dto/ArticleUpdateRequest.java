package me.hwjo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateRequest {
    private String title;
    private String content;

//    public ArticleUpdateRequest(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
}