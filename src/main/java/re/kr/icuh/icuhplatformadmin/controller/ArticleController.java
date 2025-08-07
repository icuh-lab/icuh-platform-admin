package re.kr.icuh.icuhplatformadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ArticleController {

	@GetMapping("/article/create-list")
	public String createList() {
		return "article/create-list";
	}

	@GetMapping("/article/update-list")
	public String updateList() {
		return "article/update-list";
	}

	@GetMapping("/article/delete-list")
	public String deleteList() {
		return "article/delete-list";
	}

	@GetMapping("/article/detail/{id}")
	public String articleDetail(@PathVariable("id") Long id) {
		return "article/article-detail";
	}

    @GetMapping("/article/delete-detail/{id}")
    public String articleDeleteDetail(@PathVariable("id") Long id) {
        return "article/article-delete-detail";
    }
}
