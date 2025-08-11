package re.kr.icuh.icuhplatformadmin.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.article.api.response.ArticlePendingResponse;
import re.kr.icuh.icuhplatformadmin.article.infrastructure.jpa.ArticleQueryRepository;

@Service
@RequiredArgsConstructor
public class GetCreatePendingArticleDetail {

    private final ArticleQueryRepository articleQueryRepository;

    @Transactional(readOnly = true)
    public ArticlePendingResponse findPendingArticleDetail(Long id) {
        return ArticlePendingResponse.fromEntity(articleQueryRepository.findArticle(id));
    }
}
