package com.pavansaireddy.studenteducationsystem.service;

import java.util.Set;

import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.model.Student;

public interface StudentService {
	Student getStudentByEmail(String email);
	boolean registerCourse(Student student,Courses course);
	boolean unRegisterCourse(Student student,Courses course);
	Set<Courses> getMyAllRegisteredCourses(Student student);
}
