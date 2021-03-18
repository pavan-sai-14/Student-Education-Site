package com.pavansaireddy.studenteducationsystem.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.pavansaireddy.studenteducationsystem.model.Admin;
import com.pavansaireddy.studenteducationsystem.model.Courses;
import com.pavansaireddy.studenteducationsystem.reposistory.AdminRepo;

@Service
public class AdminServiceImp implements AdminService {
	
	private AdminRepo adminRepo;

	public AdminServiceImp(AdminRepo adminRepo) {
		super();
		this.adminRepo = adminRepo;
	}

	@Override
	public Admin getAdminById(long id) {
		return adminRepo.findById(id).get();
	}

	@Override
	public void addedCourse(Admin admin,Courses latest,String add,Courses old) {
		if(add.equals("update"))
		{
			admin.getAddedCourses().remove(old);
		}
		admin.addCourse(latest);
		adminRepo.save(admin);
	}
	
	@Override
	public Admin getAdminByEmail(String email) {
		return adminRepo.findByEmail(email);
	}

	@Override
	public void removeCourseFromAdmin(String email, Courses c) {
		adminRepo.findByEmail(email).dropCourse(c);
		adminRepo.save(adminRepo.findByEmail(email));
	}

	@Override
	public boolean registerCourse(Admin admin, Courses course) {
		if(!admin.getCourseRegisteredByAdmin().stream().anyMatch(c->c.getId()==course.getId()))
		{
			admin.registerCourse(course);
			adminRepo.save(admin);
			return true;
		}
		return false;
	}

	@Override
	public Set<Courses> getMyAllRegisteredCourses(Admin admin) {
		return adminRepo.findByEmail(admin.getEmail()).getCourseRegisteredByAdmin();
	}

	@Override
	public boolean unRegisterCourse(Admin admin, Courses course) {
		if(admin.getCourseRegisteredByAdmin().stream().anyMatch(c->c.getId()==course.getId()))
		{
			admin.unRegisterCourse(course);
			adminRepo.save(admin);
			return true;
		}
		return false;
	}

}
