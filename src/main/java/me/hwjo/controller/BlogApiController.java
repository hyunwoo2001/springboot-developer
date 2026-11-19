
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

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(
            @RequestBody ArticleCreationRequest request) {

        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {

        List<ArticleResponse> articleResponseList =
                blogService.findAll().stream()
                        .map(ArticleResponse::new)
                        .toList();

        return ResponseEntity.ok().body(articleResponseList);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> getById(
            @PathVariable Long id) {

        Article article = blogService.getById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable Long id) {

        blogService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Long id,
            @RequestBody ArticleUpdateRequest request) {

        Article article = blogService.updateArticle(id, request);

        ArticleResponse response = new ArticleResponse(article);

        return ResponseEntity.ok().body(response);
    }
}
