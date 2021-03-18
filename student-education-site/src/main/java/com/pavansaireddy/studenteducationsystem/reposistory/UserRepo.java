package com.pavansaireddy.studenteducationsystem.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavansaireddy.studenteducationsystem.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByEmail(String username);
	User findByFirstName(String firstName);
}
