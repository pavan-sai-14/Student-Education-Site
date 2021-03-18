package com.pavansaireddy.studenteducationsystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavansaireddy.studenteducationsystem.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long>{
	Admin findByFirstName(String name);
	Admin findByEmail(String email);
}
