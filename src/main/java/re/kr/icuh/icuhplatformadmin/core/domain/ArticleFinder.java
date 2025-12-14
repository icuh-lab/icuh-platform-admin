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

    @Transactional(readOnly = true)
    public List<ArticleListResponse> findArticleByStatus(ArticleStatus status) {
        List<ArticleListResponse> articleListResponses = articleRepository.findArticlesByStatusOrderByCreatedAtDesc(status)
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

    @Transactional
    public void rejectArticle(Long articleId, String reason) {
        Article savedArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        savedArticle.changeStatus(ArticleStatus.REJECTED);
        savedArticle.setRejectReason(reason);
    }

    @Transactional(readOnly = true)
    public List<ArticleListResponse> pendingUpdateArticles() {
        List<ArticleListResponse> pendingUpdateArticleListResponse = articleRepository.findPendingUpdateArticle()
                .stream()
                .map(ArticleListResponse::fromEntity)
                .toList();

        return pendingUpdateArticleListResponse;
    }
}
