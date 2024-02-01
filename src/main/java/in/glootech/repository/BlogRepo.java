package in.glootech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.glootech.entity.BlogDetails;
import in.glootech.entity.UserDetails;

public interface BlogRepo extends JpaRepository<BlogDetails, Integer>{
	public List<BlogDetails> findByUserId(UserDetails userId);
}
