package com.pavansaireddy.studenteducationsystem.reposistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;

public interface CourseRepo extends JpaRepository<Courses, Long> {
	List<Courses> findAllByAdminId(Admin adminId);
	Courses findByCourseTitle(String name);
}
