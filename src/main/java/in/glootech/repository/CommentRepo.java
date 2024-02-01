package in.glootech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.glootech.entity.CommentDetails;

public interface CommentRepo extends JpaRepository<CommentDetails, Integer>{

}
