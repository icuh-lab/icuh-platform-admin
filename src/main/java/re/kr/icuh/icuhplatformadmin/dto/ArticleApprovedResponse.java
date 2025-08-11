package re.kr.icuh.icuhplatformadmin.dto;

import re.kr.icuh.icuhplatformadmin.domain.article.Article;
import re.kr.icuh.icuhplatformadmin.domain.article.ArticleStatus;
import re.kr.icuh.icuhplatformadmin.domain.file.FileStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ArticleApprovedResponse(
        Long id,
        String title,
        String description,
        String author,
        String authorOrganization,
        String department,
        ArticleStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views,
        DocumentTypeResponse classification,
        SubjectDomainResponse serviceType,
        List<FileResponse> files
) {
    public static ArticlePendingResponse fromEntity(Article article) {
        return new ArticlePendingResponse(
                article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getAuthor(),
                article.getAuthorOrganization(),
                article.getDepartment(),
                article.getStatus(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getViews(),
                DocumentTypeResponse.fromEntity(article.getDocumentType()),
                SubjectDomainResponse.fromEntity(article.getSubjectDomain()),
                article.getFiles().stream()
                        .filter(file -> file.getStatus() == FileStatus.APPROVED)
                        .map(FileResponse::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
