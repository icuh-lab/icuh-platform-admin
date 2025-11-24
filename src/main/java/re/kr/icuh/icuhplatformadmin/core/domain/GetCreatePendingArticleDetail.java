package re.kr.icuh.icuhplatformadmin.core.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.core.api.controller.v1.response.ArticlePendingResponse;

@Service
@RequiredArgsConstructor
public class GetCreatePendingArticleDetail {

    private final ArticleQueryRepository articleQueryRepository;

    @Transactional(readOnly = true)
    public ArticlePendingResponse findPendingArticleDetail(Long id) {
        return ArticlePendingResponse.fromEntity(articleQueryRepository.findArticle(id));
    }
}
