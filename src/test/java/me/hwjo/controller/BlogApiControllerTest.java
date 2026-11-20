
package me.hwjo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hwjo.dao.Article;
import me.hwjo.dto.ArticleCreationRequest;
import me.hwjo.dto.ArticleUpdateRequest;
import me.hwjo.repository.BlogRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BlogApiControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach
    void setUp() {
        blogRepository.deleteAll();
    }

    // 1. 글 생성 테스트
    @Test
    @DisplayName("addArticle: 글 생성에 성공한다")
    void addArticle() throws Exception {

        // Given
        ArticleCreationRequest request = new ArticleCreationRequest(
                "테스트 제목",
                "테스트 내용"
        );

        String json = objectMapper.writeValueAsString(request);

        // When
        mockMvc.perform(post("/api/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                // Then
                .andExpect(status().isCreated());

        List<Article> articles = blogRepository.findAll();

        assertEquals(1, articles.size());
        assertEquals(request.getTitle(), articles.get(0).getTitle());
        assertEquals(request.getContent(), articles.get(0).getContent());
    }

    // 2. 글 조회 테스트
    @Test
    @DisplayName("findArticle: 글 한 건을 조회한다")
    void findArticle() throws Exception {

        // Given
        Article article = blogRepository.save(
                Article.builder()
                        .title("조회 제목")
                        .content("조회 내용")
                        .build()
        );

        // When
        mockMvc.perform(get("/api/articles/{id}", article.getId()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("조회 제목"))
                .andExpect(jsonPath("$.content").value("조회 내용"));
    }

    // 3. 글 수정 테스트
    @Test
    @DisplayName("updateArticle: 글 수정에 성공한다")
    void updateArticle() throws Exception {

        // Given
        Article article = blogRepository.save(
                Article.builder()
                        .title("수정 전 제목")
                        .content("수정 전 내용")
                        .build()
        );

        ArticleUpdateRequest request = new ArticleUpdateRequest(
                "수정 후 제목",
                "수정 후 내용"
        );

        String json = objectMapper.writeValueAsString(request);

        // When
        mockMvc.perform(put("/api/articles/{id}", article.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                // Then
                .andExpect(status().isOk());

        Article updatedArticle = blogRepository.findById(article.getId())
                .orElseThrow();

        assertEquals("수정 후 제목", updatedArticle.getTitle());
        assertEquals("수정 후 내용", updatedArticle.getContent());
    }

    // 4. 글 삭제 테스트
    @Test
    @DisplayName("deleteArticle: 글 삭제에 성공한다")
    void deleteArticle() throws Exception {

        // Given
        Article article = blogRepository.save(
                Article.builder()
                        .title("삭제 제목")
                        .content("삭제 내용")
                        .build()
        );

        // When
        mockMvc.perform(delete("/api/articles/{id}", article.getId()))
                // Then
                .andExpect(status().isOk());

        List<Article> articles = blogRepository.findAll();

        assertThat(articles).isEmpty();
    }

    // 5. 전체 글 조회 테스트
    @Test
    @DisplayName("findAllArticles: 전체 글 목록을 조회한다")
    void findAllArticles() throws Exception {

        // Given
        blogRepository.save(
                Article.builder()
                        .title("첫 번째 제목")
                        .content("첫 번째 내용")
                        .build()
        );

        blogRepository.save(
                Article.builder()
                        .title("두 번째 제목")
                        .content("두 번째 내용")
                        .build()
        );

        // When
        mockMvc.perform(get("/api/articles"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("첫 번째 제목"))
                .andExpect(jsonPath("$[0].content").value("첫 번째 내용"))
                .andExpect(jsonPath("$[1].title").value("두 번째 제목"))
                .andExpect(jsonPath("$[1].content").value("두 번째 내용"));
    }
}
