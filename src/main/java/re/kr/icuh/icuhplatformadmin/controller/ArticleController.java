package re.kr.icuh.icuhplatformadmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
