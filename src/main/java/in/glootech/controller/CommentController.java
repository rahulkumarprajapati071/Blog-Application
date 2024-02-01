package in.glootech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.glootech.entity.BlogDetails;
import in.glootech.entity.CommentDetails;
import in.glootech.service.BlogService;
import in.glootech.service.CommentService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private HttpSession session;
	
	@PostMapping("/blog/{blogId}")
	public String addComment(@PathVariable Integer blogId, @ModelAttribute("comment") CommentDetails commentDetails,Model model) {
		//TODO: add comments in database and show in blogPage also...
		BlogDetails blogs = blogService.getBlogById(blogId);
		commentDetails.setBlogId(blogs);
		boolean addComments = commentService.addComments(commentDetails);
		if(addComments) {
			model.addAttribute("succMsg","Added Your Comment");
		}else {
			model.addAttribute("errMsg","Failed to add Comment");
		}
		BlogDetails blog = blogService.getBlogById(blogId);
	    List<CommentDetails> comments = commentService.getCommentsForBlog(blogId);
	    model.addAttribute("blog", blog);
	    model.addAttribute("comment", new CommentDetails());
	    model.addAttribute("comments", comments);
	    
	    if(session.getAttribute("userId") != null) {
	    	return "blogPage";
	    }else {
	    	return "indexBlogPage";
	    }
	}
	
	@GetMapping("/viewComments")
	public String getAllCommentsPage(Model model) {
		List<CommentDetails> allComments = commentService.getAllComments();
		model.addAttribute("viewComments",allComments);
		return "viewComments";
	}
	
	@GetMapping("/deleteComment/{commentId}")
	public String deleteComment(@PathVariable Integer commentId, Model model) {
		boolean deleteComment = commentService.deleteComment(commentId);
		if(deleteComment) {
			model.addAttribute("succMsg","Deletion Succussfuly");
		}else {
			model.addAttribute("errMsg","Deletion Failed");
		}
		List<CommentDetails> allComments = commentService.getAllComments();
		model.addAttribute("viewComments",allComments);
		return "viewComments";
	}
}
