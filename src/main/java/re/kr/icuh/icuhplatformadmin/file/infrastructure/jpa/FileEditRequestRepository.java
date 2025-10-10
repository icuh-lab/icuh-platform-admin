package re.kr.icuh.icuhplatformadmin.file.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import re.kr.icuh.icuhplatformadmin.file.domain.FileEditRequest;

@Repository
public interface FileEditRequestRepository extends JpaRepository<FileEditRequest, Long>{
}
