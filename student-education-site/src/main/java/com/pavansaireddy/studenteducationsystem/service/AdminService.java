package com.pavansaireddy.studenteducationsystem.service;

import java.util.Set;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;

public interface AdminService {
	Admin getAdminById(long id);
	void addedCourse(Admin admin,Courses latest,String add,Courses old);
	Admin getAdminByEmail(String email);
	void removeCourseFromAdmin(String email,Courses c);
	boolean registerCourse(Admin admin,Courses course);
	boolean unRegisterCourse(Admin admin,Courses course);
	Set<Courses> getMyAllRegisteredCourses(Admin admin);
}
