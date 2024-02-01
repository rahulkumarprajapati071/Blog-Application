package in.glootech.service;

import in.glootech.binding.LoginDetails;
import in.glootech.entity.UserDetails;

public interface UserService {
	public boolean registerUser(UserDetails user);
	public boolean loginUser(LoginDetails user);
}
