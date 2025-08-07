package re.kr.icuh.icuhplatformadmin.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import re.kr.icuh.icuhplatformadmin.domain.Article;
import re.kr.icuh.icuhplatformadmin.domain.ArticleStatus;
import re.kr.icuh.icuhplatformadmin.domain.FileStatus;
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
}
