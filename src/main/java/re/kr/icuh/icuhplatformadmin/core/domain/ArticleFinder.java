package re.kr.icuh.icuhplatformadmin.core.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response.ArticleListResponse;
import re.kr.icuh.icuhplatformadmin.core.api.controller.v2.response.ArticleResponse;

import java.util.List;

@Component
public class ArticleFinder {

    private final ArticleRepository articleRepository;

    public ArticleFinder(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional(readOnly = true)
    public List<ArticleListResponse> findArticleByStatus(ArticleStatus status) {
        return articleRepository.findArticlesByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(ArticleListResponse::fromEntity)
                .toList();
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
        return articleRepository.findPendingUpdateArticle()
                .stream()
                .map(ArticleListResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true) // 여기서 새로 변경될 데이터가 노출이 되도록해야함
    public ArticleResponse findArticle(Long articleId) {
        Article savedArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        return ArticleResponse.fromEntity(savedArticle);
    }

    @Transactional
    public void mergeArticle(Long articleId) {
        Article savedArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article not found"));

        if (savedArticle.getPendingUpdate() == null) {
            throw new IllegalArgumentException("Article not pending update");
        }

        // PendingUpdate 컬럼의 내용을 다시 역직렬화하여 객체로 전환 후 업데이트
        savedArticle.updateArticleV2(savedArticle.getPendingUpdate());
        savedArticle.initPendingUpdate();
    }
}
