package re.kr.icuh.icuhplatformadmin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import re.kr.icuh.icuhplatformadmin.dto.ArticleListResponse;
import re.kr.icuh.icuhplatformadmin.dto.ArticlePendingResponse;
import re.kr.icuh.icuhplatformadmin.service.ArticleService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class ArticleApiController {

    private final ArticleService articleService;

    @GetMapping("/articles/pending")
    public List<ArticleListResponse> findPendingArticles() {

        return articleService.findPendingArticles();
    }

    @GetMapping("/articles/approved")
    public List<ArticleListResponse> findApprovedArticles() {
        return articleService.findApprovedArticles();
    }

    @GetMapping("/articles/{id}")
    public ArticlePendingResponse findArticle(@PathVariable Long id) {
        return articleService.findArticle(id);
    }

    // 게시글 승인 버튼 api
    @PostMapping("/articles/{id}")
    public void approveArticle(@PathVariable Long id) {
        articleService.approveArticle(id);
    }

    @GetMapping("/articles/delete-list")
    public List<ArticleListResponse> findDeletePendingArticles() {
        return articleService.findDeletePendingArticles();
    }

    @GetMapping("/articles/delete-approved")
    public List<ArticleListResponse> findDeleteApprovedArticles() {
        return articleService.findDeleteApprovedArticles();
    }

    // 게시글 삭제 승인 버튼 api
    @PatchMapping("/articles/{id}")
    public void deleteApproveArticle(@PathVariable Long id) {
        articleService.deleteApproveArticle(id);
    }

    @GetMapping("/articles/updated-pending")
    public List<ArticleListResponse> findUpdatedPendingArticles() {
        return articleService.findUpdatedPendingArticles();
    }
}
