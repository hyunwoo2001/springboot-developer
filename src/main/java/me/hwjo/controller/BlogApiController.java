package me.hwjo.controller;

import lombok.RequiredArgsConstructor;
import me.hwjo.dao.Article;
import me.hwjo.dto.ArticleCreationRequest;
import me.hwjo.dto.ArticleResponse;
import me.hwjo.dto.ArticleUpdateRequest;
import me.hwjo.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    // 1. 블로그 글 생성 API
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody ArticleCreationRequest articleCreationRequest) {
        Article savedArticle = blogService.save(articleCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    // 2. 전체 블로그 글 목록 조회 API
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        List<ArticleResponse> articleResponseList = blogService.findAll().stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articleResponseList);
    }

    // 3. 특정 ID의 블로그 글 조회 API
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) {
        Article article = blogService.getById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        blogService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateRequest request) {
        Article article = blogService.updateArticle(id, request);
        ArticleResponse articleResponse = new ArticleResponse(article);
        return ResponseEntity.ok().body(articleResponse);
    }
}