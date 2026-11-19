import me.hwjo.dao.Article;
import me.hwjo.dto.ArticleCreationRequest;
import me.hwjo.dto.ArticleUpdateRequest;
import me.hwjo.repository.BlogRepository;
import me.hwjo.service.BlogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogService blogService;

    // 게시글 생성 테스트
    @Test
    void saveArticleTest() {
        ArticleCreationRequest request =
                new ArticleCreationRequest("제목", "내용");

        Article article = request.toEntity();

        when(blogRepository.save(any(Article.class)))
                .thenReturn(article);

        Article result = blogService.save(request);

        assertEquals("제목", result.getTitle());
        assertEquals("내용", result.getContent());
    }

    // 전체 게시글 조회 테스트
    @Test
    void findAllTest() {
        List<Article> articles = List.of(
                new Article("제목1", "내용1"),
                new Article("제목2", "내용2")
        );

        when(blogRepository.findAll()).thenReturn(articles);

        List<Article> result = blogService.findAll();

        assertEquals(2, result.size());
    }

    // 특정 게시글 조회 테스트
    @Test
    void getByIdTest() {
        Article article = new Article("제목", "내용");

        when(blogRepository.findById(1L))
                .thenReturn(Optional.of(article));

        Article result = blogService.getById(1L);

        assertEquals("제목", result.getTitle());
    }

    // 게시글 수정 테스트
    @Test
    void updateArticleTest() {
        Article article = new Article("기존 제목", "기존 내용");

        when(blogRepository.findById(1L))
                .thenReturn(Optional.of(article));

        ArticleUpdateRequest request =
                new ArticleUpdateRequest("수정 제목", "수정 내용");

        Article result = blogService.updateArticle(1L, request);

        assertEquals("수정 제목", result.getTitle());
        assertEquals("수정 내용", result.getContent());
    }

    // 게시글 삭제 테스트
    @Test
    void deleteArticleTest() {
        doNothing().when(blogRepository).deleteById(1L);

        blogService.deleteById(1L);

        verify(blogRepository).deleteById(1L);
    }
}
