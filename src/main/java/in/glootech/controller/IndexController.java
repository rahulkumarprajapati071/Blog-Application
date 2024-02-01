package in.glootech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.glootech.entity.BlogDetails;
import in.glootech.service.BlogService;

@Controller
public class IndexController {
	
	@Autowired
	private BlogService service;
	
	@GetMapping("/")
	public String getIndexBlogs(Model model) {
		List<BlogDetails> userBlogs = service.getAllBlog();
		model.addAttribute("blogs",userBlogs);
		return "index";
	}
}
