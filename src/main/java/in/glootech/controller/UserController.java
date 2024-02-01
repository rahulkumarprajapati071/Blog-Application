package in.glootech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.glootech.binding.LoginDetails;
import in.glootech.entity.UserDetails;
import in.glootech.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	@Autowired
	private HttpSession session;

	@GetMapping("/signup")
	public String getSignup(Model model) {
		model.addAttribute("user",new UserDetails());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signUp(@ModelAttribute("user") UserDetails registrationForm,Model model) {
		System.out.println(registrationForm);
		boolean user = service.registerUser(registrationForm);
		if(user) {
			model.addAttribute("succMsg","Account created now you can login");
		}else {
			model.addAttribute("errMsg","Email already Exist");
		}
		return "signup";
	}

	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("user",new LoginDetails());
		return "login";
	}
	
	@PostMapping("/login")
	public String getLogin(@ModelAttribute("user") LoginDetails loginDetails, Model model) {
		boolean loginUser = service.loginUser(loginDetails);
		if(loginUser) {
			return "redirect:/dashboard";
		}else {
			model.addAttribute("errMsg","Invalid Login Details");
		}
		return "login";
	}
	
	@GetMapping("/logout")
	public String getLogout() {
		session.invalidate();
		return "index";
	}
	
}
