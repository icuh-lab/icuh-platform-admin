package re.kr.icuh.icuhplatformadmin.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectDomainRepository extends JpaRepository<SubjectDomain, Long> {
    Optional<SubjectDomain> findByCode(String code);
}
