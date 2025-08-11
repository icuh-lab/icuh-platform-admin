package re.kr.icuh.icuhplatformadmin.article.api.response;

import re.kr.icuh.icuhplatformadmin.article.domain.DocumentType;

public record DocumentTypeResponse(
        Long id,
        String name
) {
    public static DocumentTypeResponse fromEntity(DocumentType documentType) {
        return new DocumentTypeResponse(
                documentType.getId(),
                documentType.getName()
        );
    }
}
