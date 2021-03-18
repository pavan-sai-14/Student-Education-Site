package com.pavansaireddy.studenteducationsystem.service;

import java.util.Arrays;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.model.Roles;
import com.pavansaireddy.studenteducationsystem.model.Student;
import com.pavansaireddy.studenteducationsystem.model.User;
import com.pavansaireddy.studenteducationsystem.reposistory.AdminRepo;
import com.pavansaireddy.studenteducationsystem.reposistory.StudentRepo;
import com.pavansaireddy.studenteducationsystem.reposistory.UserRepo;

@Service
public class UserServiceImp implements UserService{

	private UserRepo userRepo;
	
	private StudentRepo studentRepo;
	
	private AdminRepo adminRepo;
	
	public UserServiceImp(UserRepo userRepo, StudentRepo studentRepo, AdminRepo adminRepo) {
		super();
		this.userRepo = userRepo;
		this.studentRepo = studentRepo;
		this.adminRepo = adminRepo;
	}

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
 
	@Override
	public void save(User user) {
		if(user.getRole().equals(Roles.USER_STUDENT))
		{
			Student student=new Student(user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getEmail(),
					passwordEncoder.encode(user.getPassword()),
					0,
					new TreeSet<Courses>());
			studentRepo.save(student);
		}
		else
		{
			Admin admin=new Admin(user.getId(),
					user.getFirstName(),
					user.getLastName(),
					user.getEmail(),
					passwordEncoder.encode(user.getPassword()),
					0,
					0,
					new TreeSet<Courses>(),
					new TreeSet<Courses>());
			adminRepo.save(admin);
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepo.findByEmail(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Invalid USER or PASSWORD");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString())));		
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

}
