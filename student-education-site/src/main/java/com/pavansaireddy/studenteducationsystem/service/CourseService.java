package com.pavansaireddy.studenteducationsystem.service;

import java.util.List;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.model.Student;

public interface CourseService {
	void addCourse(Courses course);
	List<Courses> showAllCourses(String role,String name);
	List<Courses> getByAdminName(String adminName);
	List<Courses> getMyAllCourses(String email);
	void removeCourse(long id);
	Courses getCoursebyId(long id);
	void courseRegisterByAdmin(Admin admin,Courses course);
	void courseRegisterByStudent(Student student,Courses course);
	void courseUnRegisterByAdmin(Admin admin,Courses course);
	void courseUnRegisterByStudent(Student student,Courses course);
}
