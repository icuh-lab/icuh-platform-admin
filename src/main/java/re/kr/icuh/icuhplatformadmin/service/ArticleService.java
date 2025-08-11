package re.kr.icuh.icuhplatformadmin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.domain.article.Article;
import re.kr.icuh.icuhplatformadmin.domain.article.ArticleEditRequest;
import re.kr.icuh.icuhplatformadmin.domain.article.ArticleStatus;
import re.kr.icuh.icuhplatformadmin.domain.file.FileStatus;
import re.kr.icuh.icuhplatformadmin.dto.ArticleListResponse;
import re.kr.icuh.icuhplatformadmin.dto.ArticlePendingResponse;
import re.kr.icuh.icuhplatformadmin.repository.ArticleQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleQueryRepository articleQueryRepository;

    public List<ArticleListResponse> findPendingArticles() {
        List<Article> pendingArticles = articleQueryRepository.findPendingArticles();

        List<ArticleListResponse> articleListResponses = pendingArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());

        return articleListResponses;

    }

    public List<ArticleListResponse> findApprovedArticles() {
        List<Article> approvedArticles = articleQueryRepository.findApprovedArticles();

        List<ArticleListResponse> articleListResponses = approvedArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());

        return articleListResponses;
    }

    public ArticlePendingResponse findArticle(Long id) {
        Article article = articleQueryRepository.findArticle(id);

        return ArticlePendingResponse.fromEntity(article);
    }

    public ArticlePendingResponse findApprovedArticle(Long id) {
        Article article = articleQueryRepository.findArticle(id);
        return ArticlePendingResponse.fromEntity(article);
    }

    @Transactional
    public void approveArticle(Long id) {
        Article article = articleQueryRepository.findArticle(id);

        article.changeStatus(ArticleStatus.APPROVED);

        article.getFiles().stream()
                .filter(file -> file.getStatus() == FileStatus.PENDING)
                .forEach(file -> file.changeStatus(FileStatus.APPROVED));
    }

    /**
     * 삭제 요청 리스트
     * @return
     */
    public List<ArticleListResponse> findDeletePendingArticles() {
        List<Article> deletePendingArticles = articleQueryRepository.findDeletePendingArticles();

        return deletePendingArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ArticleListResponse> findDeleteApprovedArticles() {
        List<Article> deletePendingArticles = articleQueryRepository.findDeleteApprovedArticles();

        return deletePendingArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteApproveArticle(Long id) {
        Article article = articleQueryRepository.findArticle(id);

        article.changeStatus(ArticleStatus.DELETED);

        article.getFiles().stream()
                .filter(file -> file.getStatus() == FileStatus.DELETED_PENDING)
                .forEach(file -> file.changeStatus(FileStatus.DELETED));
    }

    @Transactional(readOnly = true)
    public List<ArticleListResponse> findUpdatedPendingArticles() {
        List<Article> updatedPendingArticles = articleQueryRepository.findUpdatedPendingArticles();

        return updatedPendingArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateApprovedArticle(Long id) {
        // 원본 글 select
        Article article = articleQueryRepository.findArticle(id);

        // 업데이트 될 글 select
        ArticleEditRequest articleEditRequest = articleQueryRepository.findUpdatedRequestArticle(id);

        // 덮어쓰기
        article.updateArticle(articleEditRequest);
    }

    @Transactional(readOnly = true)
    public List<ArticleListResponse> findUpdatedApprovedArticles() {
        List<Article> updatedApprovedArticles = articleQueryRepository.findUpdatedApprovedArticles();

        return updatedApprovedArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
