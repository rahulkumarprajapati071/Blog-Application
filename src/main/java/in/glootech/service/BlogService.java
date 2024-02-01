package in.glootech.service;

import java.util.List;

import org.hibernate.annotations.Comments;

import in.glootech.entity.BlogDetails;
import in.glootech.entity.CommentDetails;

public interface BlogService{
	public boolean createBlog(BlogDetails blogDetails);
	public List<BlogDetails> getAllBlog();
	public List<BlogDetails> getForUser();
	public BlogDetails getBlogById(Integer blogId);
	public boolean deleteBlog(Integer blogId);
}
