package re.kr.icuh.icuhplatformadmin.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.article.api.response.ArticleEditRequestListResponse;
import re.kr.icuh.icuhplatformadmin.article.api.response.ArticleListResponse;
import re.kr.icuh.icuhplatformadmin.article.infrastructure.jpa.ArticleQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListUpdatePendingArticles {

    private final ArticleQueryRepository articleQueryRepository;

    public List<ArticleEditRequestListResponse> findUpdatePendingArticles() {

        return articleQueryRepository.findUpdatedPendingArticles().stream()
                .map(ArticleEditRequestListResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
