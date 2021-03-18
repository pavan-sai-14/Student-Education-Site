package com.pavansaireddy.studenteducationsystem.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.model.Student;
import com.pavansaireddy.studenteducationsystem.reposistory.StudentRepo;

@Service
public class StudentServiceImp implements StudentService {
	
	private StudentRepo studentRepo;

	public StudentServiceImp(StudentRepo studentRepo) {
		super();
		this.studentRepo = studentRepo;
	}

	@Override
	public Student getStudentByEmail(String email) {
		return studentRepo.findByEmail(email);
	}

	@Override
	public boolean registerCourse(Student student,Courses course) {
		if(!student.getCourseRegisteredByStudent().stream().anyMatch(s->s.getId()==course.getId()))
		{
			student.getCourseRegisteredByStudent().add(course);
			student
			.setTotalRegisteredCourses(student.getCourseRegisteredByStudent().size());
			studentRepo.save(student);
			return true;
		}
		return false;
	}

	@Override
	public Set<Courses> getMyAllRegisteredCourses(Student student) {
		return studentRepo.findByEmail(student.getEmail()).getCourseRegisteredByStudent();
	}

	@Override
	public boolean unRegisterCourse(Student student, Courses course) {
		if(student.getCourseRegisteredByStudent().stream().anyMatch(s->s.getId()==course.getId()))
		{
			student.getCourseRegisteredByStudent().remove(course);
			student
			.setTotalRegisteredCourses(student.getCourseRegisteredByStudent().size());
			studentRepo.save(student);
			return true;
		}
		return false;
	}

}
