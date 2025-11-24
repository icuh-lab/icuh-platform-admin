package re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response;

import re.kr.icuh.icuhplatformadmin.core.domain.SubjectDomain;

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
