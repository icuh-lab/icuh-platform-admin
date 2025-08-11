package re.kr.icuh.icuhplatformadmin.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.article.api.response.ArticleListResponse;
import re.kr.icuh.icuhplatformadmin.article.domain.Article;
import re.kr.icuh.icuhplatformadmin.article.infrastructure.jpa.ArticleQueryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCreatePendingArticles {

    private final ArticleQueryRepository articleQueryRepository;

    @Transactional(readOnly = true)
    public List<ArticleListResponse> findPendingArticles() {
        List<Article> pendingArticles = articleQueryRepository.findPendingArticles();

        return pendingArticles.stream()
                .map(ArticleListResponse::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }
}
