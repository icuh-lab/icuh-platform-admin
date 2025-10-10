package re.kr.icuh.icuhplatformadmin.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.article.domain.Article;
import re.kr.icuh.icuhplatformadmin.article.domain.ArticleEditRequest;
import re.kr.icuh.icuhplatformadmin.article.domain.ArticleStatus;
import re.kr.icuh.icuhplatformadmin.article.infrastructure.jpa.ArticleQueryRepository;

@Service
@RequiredArgsConstructor
public class ApproveUpdateArticle {

    private final ArticleQueryRepository articleQueryRepository;

    @Transactional
    public void approveUpdateArticle(Long id) {
        ArticleEditRequest articleEditRequest = articleQueryRepository.findUpdatedRequestArticle(id);
        Article article = articleQueryRepository.findArticle(articleEditRequest.getArticle().getId());


        article.updateArticle(articleEditRequest);
        articleEditRequest.changeStatus(ArticleStatus.UPDATED_APPROVED);
    }
}
