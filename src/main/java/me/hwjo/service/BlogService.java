package me.hwjo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hwjo.dao.Article;
import me.hwjo.dto.ArticleCreationRequest;
import me.hwjo.dto.ArticleUpdateRequest;
import me.hwjo.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 생성
    public Article save(ArticleCreationRequest articleCreationRequest) {
        return blogRepository.save(articleCreationRequest.toEntity());
    }

    // 전체 블로그 글 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    // 특정 ID로 블로그 글 조회
    public Article getById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 글이 존재하지 않습니다. id: " + id));
    }

    // 블로그 글 삭제
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    // 블로그 글 수정
    @Transactional
    public Article updateArticle(Long id, ArticleUpdateRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        article.update(request.getTitle(), request.getContent());
        return article;
    }
}