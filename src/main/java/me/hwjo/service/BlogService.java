
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

    public Article save(ArticleCreationRequest request) {
        return blogRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article getById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 글이 존재하지 않습니다. id: " + id));
    }

    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article updateArticle(Long id, ArticleUpdateRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "not found : " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
