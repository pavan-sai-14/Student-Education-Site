package com.pavansaireddy.studenteducationsystem.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Roles;
import com.pavansaireddy.studenteducationsystem.model.Student;
import com.pavansaireddy.studenteducationsystem.model.User;
import com.pavansaireddy.studenteducationsystem.service.AdminService;
import com.pavansaireddy.studenteducationsystem.service.CourseService;
import com.pavansaireddy.studenteducationsystem.service.StudentService;
import com.pavansaireddy.studenteducationsystem.service.UserService;


@Controller
public class LoginController {
	
	private CourseService courseService;
	
	private UserService userService;
	
	private AdminService adminService;
	
	private StudentService studentService;
	
	public LoginController(CourseService courseService, UserService userService, AdminService adminService,
			StudentService studentService) {
		super();
		this.courseService = courseService;
		this.userService = userService;
		this.adminService = adminService;
		this.studentService = studentService;
	}

	@GetMapping("/login")
	public String login() {
		System.out.println("caling........................");
		return "login";
	}
	
	@ModelAttribute("admin")
	public Admin admin()
	{
		return new Admin();
	}
	
	@GetMapping("/")
	public String home(Model model) {
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.getUserByEmail(auth.getName());
		model.addAttribute("role", user.getRole().toString());
		model.addAttribute("goback",0);
		model.addAttribute("userName", user.getFirstName());
		model.addAttribute("listCourses",courseService.showAllCourses(user.getRole().toString(),user.getFirstName()));
		return "courses";
	}
	
	@GetMapping("/profile")
	public String showProfile(Model model)
	{
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.getUserByEmail(auth.getName());
		if(user.getRole().equals(Roles.USER_ADMIN))
		{
			Admin admin=adminService.getAdminByEmail(user.getEmail());
			model.addAttribute("users",admin);
			System.out.println(admin+"**************");
		}
		else
		{
			Student student=studentService.getStudentByEmail(user.getEmail());
			model.addAttribute("users", student);
			
		}
		model.addAttribute("role", user.getRole().toString());
		return "display";
	}
	
}
