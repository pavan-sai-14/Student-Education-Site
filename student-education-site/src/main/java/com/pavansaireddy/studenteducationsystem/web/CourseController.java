package com.pavansaireddy.studenteducationsystem.web;

import java.util.TreeSet;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.model.Roles;
import com.pavansaireddy.studenteducationsystem.model.Student;
import com.pavansaireddy.studenteducationsystem.model.User;
import com.pavansaireddy.studenteducationsystem.service.AdminServiceImp;
import com.pavansaireddy.studenteducationsystem.service.CourseService;
import com.pavansaireddy.studenteducationsystem.service.StudentService;
import com.pavansaireddy.studenteducationsystem.service.UserService;
import com.pavansaireddy.studenteducationsystem.web.dto.CourseDTO;

@Controller
@RequestMapping("/course")
public class CourseController {
	
	private long id;
	
	private String add;
	
	private CourseService courseService;
	
	private UserService userService;

	private AdminServiceImp adminService;
	
	private StudentService studentService;

	public CourseController(CourseService courseService, UserService userService, AdminServiceImp adminService,
			StudentService studentService) {
		super();
		this.courseService = courseService;
		this.userService = userService;
		this.adminService = adminService;
		this.studentService = studentService;
	}

	@ModelAttribute("admin")
	public Admin admin()
	{
		return new Admin();
	}
	
	@GetMapping("/mycourses")
	public String showMyCourses(Model model)
	{
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		Admin admin=adminService.getAdminByEmail(auth.getName());
		model.addAttribute("role","USER_ADMIN");
		model.addAttribute("goback",1);
		model.addAttribute("userName", admin.getFirstName());
		model.addAttribute("listCourses",courseService.getMyAllCourses(admin.getEmail()));
		model.addAttribute("drop",0);
		return "courses";
	}
	
	@GetMapping("/search")
	public String ShowCourseByAdminName(@ModelAttribute("admin")Admin admin,Model model)
	{
		model.addAttribute("listCourses",courseService.getByAdminName(admin.getFirstName()));
		return "courses";
	}
	
	@ModelAttribute("course")
	public CourseDTO courseDTO()
	{
		return new CourseDTO();
	}
	
	
	@GetMapping
	public String showRegistration(Model model)
	{
		model.addAttribute("add", "add");
		this.add="add";
		return "addcourse";
	}
	
	@GetMapping("/update")
	public String updateMyCourse(@RequestParam(value="id")long id,Model model)
	{
		model.addAttribute("add", "update");
		this.add="update";
		this.id=id;
		return "addcourse";
	}
	
	@GetMapping("/remove")
	public String removeMyCourse(@RequestParam(value="id")long id,Model model)
	{
		Courses course=courseService.getCoursebyId(id);
		adminService.removeCourseFromAdmin(course.getAdminId().getEmail(), course);
		courseService.removeCourse(id);
		return this.showMyCourses(model);
	}
	
	@GetMapping("/add")
	public String saveUser(@ModelAttribute("course")CourseDTO course,Model model)
	{
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		Admin admin=adminService.getAdminByEmail(auth.getName());
		Courses old=null;
		if(add.equals("update"))
		{
			old=courseService.getCoursebyId(id);
		}
		adminService.addedCourse(adminService.getAdminById(admin.getId()),new Courses(
				id,
				course.getTitle(),
				course.getDescription(),
				adminService.getAdminById(admin.getId()),
				new TreeSet<Admin>(),
				new TreeSet<Student>()
				),this.add,old);
		courseService.addCourse(new Courses(
				id,
				course.getTitle(),
				course.getDescription(),
				adminService.getAdminById(admin.getId()),
				new TreeSet<Admin>(),
				new TreeSet<Student>()
				));
		model.addAttribute("add", "add");
		this.add="add";
		return "addcourse";
	}
	
	@GetMapping("/register")
	public String registerCourse(@RequestParam(value="id")long id,Model model)
	{
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.getUserByEmail(auth.getName());
		if(user.getRole().equals(Roles.USER_ADMIN))
		{
			Admin admin=adminService.getAdminByEmail(user.getEmail());
			Courses course=courseService.getCoursebyId(id);
			if(adminService.registerCourse(admin, course))
			{
				courseService.courseRegisterByAdmin(admin, course);
			}
		}
		else
		{
			Student student=studentService.getStudentByEmail(user.getEmail());
			Courses course=courseService.getCoursebyId(id);
			if(studentService.registerCourse(student, course))
			{
				courseService.courseRegisterByStudent(student, course);
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/unregister")
	public String dropregisteredCourse(@RequestParam(value="id")long id,Model model)
	{
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.getUserByEmail(auth.getName());
		if(user.getRole().equals(Roles.USER_ADMIN))
		{
			Admin admin=adminService.getAdminByEmail(user.getEmail());
			Courses course=courseService.getCoursebyId(id);
			if(adminService.unRegisterCourse(admin, course))
			{
				courseService.courseUnRegisterByAdmin(admin, course);
			}
		}
		else
		{
			Student student=studentService.getStudentByEmail(user.getEmail());
			Courses course=courseService.getCoursebyId(id);
			if(studentService.unRegisterCourse(student, course))
			{
				courseService.courseUnRegisterByStudent(student, course);
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/registeredcourses")
	public String showMyRegisteredCourses(Model model)
	{
		org.springframework.security.core.Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		User user=userService.getUserByEmail(auth.getName());
		if(user.getRole().equals(Roles.USER_ADMIN))
		{
			Admin admin=adminService.getAdminByEmail(user.getEmail());
			model.addAttribute("goback",1);
			model.addAttribute("userName", admin.getFirstName());
			model.addAttribute("listCourses",adminService.getMyAllRegisteredCourses(admin));
		}
		else
		{
			Student student=studentService.getStudentByEmail(user.getEmail());
			model.addAttribute("goback",1);
			model.addAttribute("userName", student.getFirstName());
			model.addAttribute("listCourses",studentService.getMyAllRegisteredCourses(student));
		}
		model.addAttribute("drop",1);
		return "courses";
	}

}
