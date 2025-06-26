package myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import myapp.service.SessionService;

@Controller
public class UserHomeController {
	
	@Autowired
	private SessionService sessionService;
	
	@GetMapping("/user")
	public String home(Model model) {
		model.addAttribute("name", sessionService.getUserName());
		return "userHome";
	}
}