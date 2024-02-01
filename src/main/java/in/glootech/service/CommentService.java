package in.glootech.service;

import java.util.List;

import in.glootech.entity.CommentDetails;

public interface CommentService {
	public List<CommentDetails> getCommentsForBlog(Integer blogId);
	public boolean addComments(CommentDetails commentDetails);
	public List<CommentDetails> getAllComments();
	public boolean deleteComment(Integer commentId);
}
