package com.pavansaireddy.studenteducationsystem.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.pavansaireddy.studenteducationsystem.model.User;

public interface UserService extends UserDetailsService {
	void save(User user);
	User getUserByEmail(String email);
}
