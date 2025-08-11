package re.kr.icuh.icuhplatformadmin.article.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import re.kr.icuh.icuhplatformadmin.article.api.response.ArticleListResponse;
import re.kr.icuh.icuhplatformadmin.article.infrastructure.jpa.ArticleQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListDeleteApprovedArticles {

    private final ArticleQueryRepository articleQueryRepository;

    @Transactional(readOnly = true)
    public List<ArticleListResponse> findDeleteApprovedArticles() {
        return articleQueryRepository.findDeleteApprovedArticles().stream()
                .map(ArticleListResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
