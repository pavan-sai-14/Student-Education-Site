package com.pavansaireddy.studenteducationsystem.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pavansaireddy.studenteducationsystem.model.User;
import com.pavansaireddy.studenteducationsystem.service.UserService;
import com.pavansaireddy.studenteducationsystem.web.dto.UserResgistrationDTO;

@Controller
@RequestMapping("/registration")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		super();
		this.service = service;
	}
	
	@ModelAttribute("user")
	public UserResgistrationDTO resgistrationDTO()
	{
		return new UserResgistrationDTO();
	}
	
	@GetMapping
	public String showRegistration()
	{
		return "registration";
	}
	
	@PostMapping
	public String saveUser(@ModelAttribute("user")UserResgistrationDTO user)
	{
		service.save(new User(user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPassword(),
				user.getRole()));
		return "redirect:/registration?success";
	}
}
