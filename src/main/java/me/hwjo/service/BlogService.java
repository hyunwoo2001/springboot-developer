package me.hwjo.service;

import lombok.RequiredArgsConstructor;
import me.hwjo.dao.Article;
import me.hwjo.dto.AddArticleRequest;
import me.hwjo.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    // 전체 게시글 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }
}