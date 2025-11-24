package re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response;

import re.kr.icuh.icuhplatformadmin.core.domain.DocumentType;

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
