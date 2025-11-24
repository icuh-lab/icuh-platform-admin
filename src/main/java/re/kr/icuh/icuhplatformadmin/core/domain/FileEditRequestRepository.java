package re.kr.icuh.icuhplatformadmin.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEditRequestRepository extends JpaRepository<FileEditRequest, Long>{
}
