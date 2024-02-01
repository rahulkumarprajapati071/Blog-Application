package in.glootech.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import in.glootech.entity.BlogDetails;
import in.glootech.entity.CommentDetails;
import in.glootech.entity.UserDetails;
import in.glootech.repository.BlogRepo;
import in.glootech.repository.CommentRepo;
import in.glootech.repository.UserRepo;
import jakarta.servlet.http.HttpSession;

@Controller
public class CommentServiceImple implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private BlogRepo blogRepo;
	@Autowired
	private HttpSession session;
	@Autowired
	private UserRepo userRepo;

	@Override
	public List<CommentDetails> getCommentsForBlog(Integer blogId) {
		Optional<BlogDetails> blogDetails = blogRepo.findById(blogId);
		if (blogDetails.isPresent()) {
			BlogDetails blogDetails2 = blogDetails.get();
			List<CommentDetails> allComments = commentRepo.findAll();

			// Filter comments for the specific blog
			allComments = allComments.stream().filter(e -> e.getBlogId().getBlogId()
						.equals(blogDetails2.getBlogId()) && e.getSoftDelete() != 0)
						.collect(Collectors.toList());

			// Reverse the order of comments
			Collections.reverse(allComments);

			return allComments;
		}
		return null;
	}

	@Override
	public boolean addComments(CommentDetails commentDetails) {
		commentDetails.setSoftDelete(1);
		CommentDetails save = commentRepo.save(commentDetails);
		return save != null;
	}

	@Override
	public List<CommentDetails> getAllComments() {
		Integer userId = (Integer)session.getAttribute("userId");
		Optional<UserDetails> userDetails = userRepo.findById(userId);
		UserDetails userDetails2 = userDetails.get();
		List<BlogDetails> userBasedBlog = blogRepo.findByUserId(userDetails2);
		System.out.println(userBasedBlog);
		List<CommentDetails> allCommentDetails = new ArrayList<>();
		for (BlogDetails blog : userBasedBlog) {
			List<CommentDetails> comments = blog.getComments();
			if(comments != null) {
				comments = comments.stream().filter(e -> e.getSoftDelete() != 0)
							.collect(Collectors.toList());
				allCommentDetails.addAll(comments);
			}
				
		}
		return allCommentDetails;
	}

	@Override
	public boolean deleteComment(Integer commentId) {
		try {
			Optional<CommentDetails> findById = commentRepo.findById(commentId);
			if(findById.isPresent()){
				CommentDetails commentDetails = findById.get();
				commentDetails.setSoftDelete(0);
				commentRepo.save(commentDetails);
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
