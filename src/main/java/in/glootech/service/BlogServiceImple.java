package in.glootech.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.glootech.entity.BlogDetails;
import in.glootech.entity.CommentDetails;
import in.glootech.entity.UserDetails;
import in.glootech.repository.BlogRepo;
import in.glootech.repository.UserRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class BlogServiceImple implements BlogService {

	@Autowired
	private BlogRepo blogRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private HttpSession session;

	@Override
	public boolean createBlog(BlogDetails blogDetails) {
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDetails> userDetailsOptional = userRepo.findById(userId);

		if (!userDetailsOptional.isPresent()) {
			return false; // User not found
		}

		UserDetails userDetails = userDetailsOptional.get();
		blogDetails.setUserId(userDetails);
		blogDetails.setSoftDelete(1);

		// Check if blogId is present in the blogDetails object
		if (blogDetails.getBlogId() != null) {
			// Perform update if blogId is present
			Optional<BlogDetails> existingBlogOptional = blogRepo.findById(blogDetails.getBlogId());

			if (existingBlogOptional.isPresent()) {
				BlogDetails existingBlog = existingBlogOptional.get();
				existingBlog.setBlogTitle(blogDetails.getBlogTitle());
				existingBlog.setBlogShort(blogDetails.getBlogShort());
				existingBlog.setBlogDesc(blogDetails.getBlogDesc());
				// Set other properties you want to update
				BlogDetails updatedBlog = blogRepo.save(existingBlog);
				return updatedBlog != null;
			}
		} else {
			// Perform create if blogId is not present
			BlogDetails savedBlog = blogRepo.save(blogDetails);
			return savedBlog != null;
		}

		return false; // Default return, indicating failure
	}

	@Override
	public List<BlogDetails> getAllBlog() {
		List<BlogDetails> findAll = blogRepo.findAll();
		findAll = findAll.stream().filter(blog -> blog.getSoftDelete() != 0).collect(Collectors.toList());
		return findAll;
	}

	@Override
	public List<BlogDetails> getForUser() {
		Integer userID = (Integer) session.getAttribute("userId");
		Optional<UserDetails> findById = userRepo.findById(userID);
		if (findById.isPresent()) {
			UserDetails userDetails = findById.get();
			List<BlogDetails> allBlogs = blogRepo.findAll();
			allBlogs = allBlogs.stream()
					.filter(e -> e.getUserId().getUserId().equals(userDetails.getUserId()) && e.getSoftDelete() != 0)
					.collect(Collectors.toList());
			return allBlogs;
		}
		return null;

	}

	@Override
	public BlogDetails getBlogById(Integer blogId) {
		Optional<BlogDetails> findById = blogRepo.findById(blogId);
		if (findById.isPresent()) {
			BlogDetails blogDetails = findById.get();
			if (blogDetails.getSoftDelete() == 1)
				return blogDetails;
			else
				return null;
		}
		return null;
	}

	@Override
	public boolean deleteBlog(Integer blogId) {
		Optional<BlogDetails> blogOptional = blogRepo.findById(blogId);

		if (blogOptional.isPresent()) {
			BlogDetails blogDetails = blogOptional.get();

			// Check if the blog has already been soft-deleted
			if (blogDetails.getSoftDelete() == 0) {
				return false; // Blog is already deleted
			}

			// Soft delete each comment with softDelete not equal to 0
			List<CommentDetails> comments = blogDetails.getComments().stream()
					.filter(comment -> comment.getSoftDelete() != 0).peek(comment -> comment.setSoftDelete(0))
					.collect(Collectors.toList());

			// Soft delete the blog
			blogDetails.setSoftDelete(0);
			blogDetails.setComments(comments);
			blogRepo.save(blogDetails);

			return true;
		}

		return false;
	}

}
