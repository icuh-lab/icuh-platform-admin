package re.kr.icuh.icuhplatformadmin.article.api.response;

import re.kr.icuh.icuhplatformadmin.article.domain.SubjectDomain;

public record SubjectDomainResponse(
        Long id,
        String name
) {
    public static SubjectDomainResponse fromEntity(SubjectDomain subjectDomain) {
        return new SubjectDomainResponse(
                subjectDomain.getId(),
                subjectDomain.getName()
        );
    }
}
