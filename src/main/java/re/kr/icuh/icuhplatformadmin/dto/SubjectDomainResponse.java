package re.kr.icuh.icuhplatformadmin.dto;

import re.kr.icuh.icuhplatformadmin.domain.SubjectDomain;

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
