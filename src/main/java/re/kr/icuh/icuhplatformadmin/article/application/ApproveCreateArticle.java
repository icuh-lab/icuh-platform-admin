package re.kr.icuh.icuhplatformadmin.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.article.domain.Article;
import re.kr.icuh.icuhplatformadmin.article.domain.ArticleStatus;
import re.kr.icuh.icuhplatformadmin.article.infrastructure.jpa.ArticleQueryRepository;
import re.kr.icuh.icuhplatformadmin.file.domain.FileStatus;

@Service
@RequiredArgsConstructor
public class ApproveCreateArticle {

    private final ArticleQueryRepository articleQueryRepository;

    @Transactional
    public void approveCreateArticle(Long id) {
        Article article = articleQueryRepository.findArticle(id);
        article.changeStatus(ArticleStatus.APPROVED);

        article.getFiles().stream()
                .filter(file -> file.getStatus() == FileStatus.PENDING)
                .forEach(file -> file.changeStatus(FileStatus.APPROVED));
    }
}
