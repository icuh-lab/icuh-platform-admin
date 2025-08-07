package re.kr.icuh.icuhplatformadmin.dto;

import re.kr.icuh.icuhplatformadmin.domain.DocumentType;

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
