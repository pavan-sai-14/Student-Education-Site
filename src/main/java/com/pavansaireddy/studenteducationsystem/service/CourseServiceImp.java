package com.pavansaireddy.studenteducationsystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.model.Student;
import com.pavansaireddy.studenteducationsystem.reposistory.AdminRepo;
import com.pavansaireddy.studenteducationsystem.reposistory.CourseRepo;
import com.pavansaireddy.studenteducationsystem.reposistory.StudentRepo;

@Service
public class CourseServiceImp implements CourseService{

	private CourseRepo courseRepo;
	private AdminRepo adminRepo;
	private StudentRepo studentRepo;

	public CourseServiceImp(CourseRepo courseRepo, AdminRepo adminRepo,StudentRepo studentRepo) {
		super();
		this.courseRepo = courseRepo;
		this.adminRepo = adminRepo;
		this.studentRepo=studentRepo;
	}

	@Override
	public void addCourse(Courses course) {
		Admin admin=course.getAdminId();
		admin.getAddedCourses().add(course);
		courseRepo.save(course);
	}

	@Override
	public List<Courses> showAllCourses(String role,String name) {
		List<Courses> course=courseRepo.findAll();
		if(role.equalsIgnoreCase("USER_ADMIN"))
		{
			return course.stream()
					.filter(c->!c.getAdminId().getFirstName().equals(name)).
					collect(Collectors.toList());
		}
		return course;
	}

	@Override
	public List<Courses> getByAdminName(String adminName) {
		return courseRepo.findAllByAdminId(adminRepo.findByFirstName(adminName));
	}

	@Override
	public List<Courses> getMyAllCourses(String email) {
		
		return courseRepo.findAll().stream()
				.filter(c->c.getAdminId().getEmail().equals(email)).
				collect(Collectors.toList());
	}

	@Override
	public void removeCourse(long id) {
		Courses c=courseRepo.findById(id).get();
		c.getCourseRegisteredByAdmins().stream().forEach(a->{
			a.getCourseRegisteredByAdmin().remove(c);
			adminRepo.save(a);
		});
		c.getCourseRegisteredByStudents().stream().forEach(s->{
			s.getCourseRegisteredByStudent().remove(c);
			studentRepo.save(s);
		});
		courseRepo.deleteById(id);
	}

	@Override
	public Courses getCoursebyId(long id) {
		return courseRepo.findById(id).get();
	}

	@Override
	public void courseRegisterByAdmin(Admin admin, Courses course) {
		course.getCourseRegisteredByAdmins().add(admin);
		courseRepo.save(course);
	}

	@Override
	public void courseRegisterByStudent(Student student, Courses course) {
		course.getCourseRegisteredByStudents().add(student);
		courseRepo.save(course);
	}
	
	@Override
	public void courseUnRegisterByAdmin(Admin admin, Courses course) {
		course.getCourseRegisteredByAdmins().remove(admin);
		courseRepo.save(course);
	}

	@Override
	public void courseUnRegisterByStudent(Student student, Courses course) {
		course.getCourseRegisteredByStudents().remove(student);
		courseRepo.save(course);
	}
}
