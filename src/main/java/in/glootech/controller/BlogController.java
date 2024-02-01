package in.glootech.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.glootech.entity.BlogDetails;
import in.glootech.entity.CommentDetails;
import in.glootech.repository.BlogRepo;
import in.glootech.service.BlogService;
import in.glootech.service.CommentService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogController {

	@Autowired
	private BlogService service;
	@Autowired
	private CommentService commentService;
	@Autowired
	private BlogRepo blogRepo;
	@Autowired
	private HttpSession session;

	@GetMapping("/createBlog")
	public String getCreateBlogPage(Model model) {
		model.addAttribute("createBlog", new BlogDetails());
		return "createBlog";
	}

	@PostMapping("createBlog")
	public String getCreateBlogPage(@ModelAttribute("createBlog") BlogDetails blogDetails, Model model) {
		
		boolean createBlog = service.createBlog(blogDetails);
		if (createBlog) {
			model.addAttribute("succMsg", "Blog Created Successfuly");
		} else {
			model.addAttribute("errMsg", "Failed to create Blog");
		}
		return "redirect:/createBlog";
	}

	@GetMapping("/editBlog/{blogId}")
	public String getEditBlog(@PathVariable Integer blogId, Model model) {
		BlogDetails blog = service.getBlogById(blogId);
		model.addAttribute("createBlog",blog);
		return "createBlog";
	}
	
	@GetMapping("/dashboard")
	public String getDashboard(Model model) {
		List<BlogDetails> userBlogs = service.getForUser();
		model.addAttribute("blogs", userBlogs);
		return "dashboard";
	}
	
	
	@GetMapping("/deleteBlog/{blogId}")
	public String deleteComment(@PathVariable Integer blogId, Model model) {
		boolean deleteBlog = service.deleteBlog(blogId);
		if(deleteBlog) {
			model.addAttribute("succMsg","Deletion Succussfuly");
		}else {
			model.addAttribute("errMsg","Deletion Failed");
		}
		List<BlogDetails> userBlogs = service.getForUser();
		model.addAttribute("blogs", userBlogs);
		return "redirect:/dashboard";
	}

	@GetMapping("/blog/{blogId}")
	public String getBlogView(@PathVariable Integer blogId, Model model) {
	    BlogDetails blog = service.getBlogById(blogId);
	    List<CommentDetails> comments = commentService.getCommentsForBlog(blogId);
	    System.out.println(comments);
	    model.addAttribute("blog", blog);
	    model.addAttribute("comment", new CommentDetails());
	    model.addAttribute("comments", comments);
	    if(session.getAttribute("userId") != null) {
	    	return "blogPage";
	    }else {
	    	return "indexBlogPage";
	    }
	}


	@GetMapping("/mustBlogs")
	public String getMustBlogPage(Model model) {
		List<BlogDetails> userBlogs = service.getAllBlog();
		model.addAttribute("blogs", userBlogs);
		return "mustBlogs";
	}
}
