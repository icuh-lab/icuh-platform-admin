package re.kr.icuh.icuhplatformadmin.core.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response.ArticleListResponse;

import java.util.List;

@Component
public class ArticleFinder {

    private final ArticleRepository articleRepository;

    public ArticleFinder(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleListResponse> findArticleByStatus(ArticleStatus status) {

        List<ArticleListResponse> articleListResponses = articleRepository.findArticlesByStatus(status)
                .stream()
                .map(ArticleListResponse::fromEntity)
                .toList();

        return articleListResponses;
    }

    @Transactional
    public void updateArticleStatus(Long articleId) {
        Article savedArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        savedArticle.changeStatus(ArticleStatus.APPROVED);
    }
}
