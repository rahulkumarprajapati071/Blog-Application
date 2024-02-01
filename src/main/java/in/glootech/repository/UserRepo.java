package in.glootech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.glootech.entity.UserDetails;

public interface UserRepo extends JpaRepository<UserDetails, Integer>{
	public UserDetails findByEmail(String email);
}
