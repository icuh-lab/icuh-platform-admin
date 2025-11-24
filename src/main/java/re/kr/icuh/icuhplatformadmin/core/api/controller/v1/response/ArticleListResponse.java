package re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response;

import re.kr.icuh.icuhplatformadmin.core.domain.Article;
import re.kr.icuh.icuhplatformadmin.core.domain.ArticleStatus;

import java.time.LocalDateTime;

public record ArticleListResponse (
        Long id,
        String title,
        String authorOrganization,
        String department,
        String author,
        ArticleStatus status,
        LocalDateTime createdAt
) {
    public static ArticleListResponse fromEntity(Article article) {
        return new ArticleListResponse(
                article.getId(),
                article.getTitle(),
                article.getAuthorOrganization(),
                article.getDepartment(),
                article.getAuthor(),
                article.getStatus(),
                article.getCreatedAt()
        );
    }
}
