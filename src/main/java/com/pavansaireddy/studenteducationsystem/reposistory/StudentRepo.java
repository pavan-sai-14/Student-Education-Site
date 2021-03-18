package com.pavansaireddy.studenteducationsystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavansaireddy.studenteducationsystem.model.Student;

public interface StudentRepo extends JpaRepository<Student, Long>{
	Student findByEmail(String email);
}
