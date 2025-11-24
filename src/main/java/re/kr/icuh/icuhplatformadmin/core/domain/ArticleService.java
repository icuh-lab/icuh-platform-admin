package re.kr.icuh.icuhplatformadmin.core.domain;

import org.springframework.stereotype.Service;
import re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response.ArticleListResponse;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleFinder articleFinder;

    public ArticleService(ArticleFinder articleFinder) {
        this.articleFinder = articleFinder;
    }

    public List<ArticleListResponse> findArticles(String status) {
        if (status.equals(ArticleStatus.PENDING.toString())) {
            return articleFinder.findArticleByStatus(ArticleStatus.PENDING);
        }
        return articleFinder.findArticleByStatus(ArticleStatus.APPROVED);
    }

    public void updateArticleStatus(Long articleId) {
        articleFinder.updateArticleStatus(articleId);
    }
}
