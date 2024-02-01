package in.glootech.service;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.glootech.binding.LoginDetails;
import in.glootech.entity.UserDetails;
import in.glootech.repository.UserRepo;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImple implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private HttpSession session;

	@Override
	public boolean registerUser(UserDetails userDetails) {
		UserDetails user = userRepo.findByEmail(userDetails.getEmail());
		if(user != null)return false;
		Encoder encoder = Base64.getEncoder();
		String encryptedPassword = encoder.encodeToString(userDetails.getPassword().getBytes());
		userDetails.setPassword(encryptedPassword);
		UserDetails save = userRepo.save(userDetails);
		return true;
	}

	@Override
	public boolean loginUser(LoginDetails user) {
		UserDetails userDetails = userRepo.findByEmail(user.getEmail());
		if(userDetails != null) {
			Decoder decoder = Base64.getDecoder();
			byte[] decode = decoder.decode(userDetails.getPassword());
			if(user.getPassword().equals(new String(decode))) {
				session.setAttribute("userId", userDetails.getUserId());
				return true;
			}
			return false;
		}
		return false;
	}

}
