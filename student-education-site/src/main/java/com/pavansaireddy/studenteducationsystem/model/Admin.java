package com.pavansaireddy.studenteducationsystem.model;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="admin",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin implements Comparator<Long>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name="total_registered_courses")
	private int totalRegisteredCourses;
	
	@Column(name = "total_added_course")
	private int totaladdedcourses;
	
	@ToString.Exclude
	@OneToMany(mappedBy = "adminId")
	private Set<Courses> addedCourses;
	
	@ToString.Exclude
	@ManyToMany()
	@JoinTable(
			name="admin_courses",
			joinColumns = @JoinColumn(name="admin_id"),
			inverseJoinColumns = @JoinColumn(name="courses_id")
			)
	private Set<Courses> courseRegisteredByAdmin;

	@Override
	public int compare(Long o1, Long o2) {
		return o1<o1?-1:o1>o2?1:0;
	}
	
	public void addCourse(Courses c)
	{
		this.getAddedCourses().add(c);
		this.setTotaladdedcourses(this.getAddedCourses().size());
	}
	
	public void dropCourse(Courses c)
	{
		this.getAddedCourses().remove(c);
		this.setTotaladdedcourses(this.getAddedCourses().size());
	}
	
	public void registerCourse(Courses c)
	{
		this.getCourseRegisteredByAdmin().add(c);
		this.setTotalRegisteredCourses(this.getCourseRegisteredByAdmin().size());
	}
	
	public void unRegisterCourse(Courses c)
	{
		this.getCourseRegisteredByAdmin().remove(c);
		this.setTotalRegisteredCourses(this.getCourseRegisteredByAdmin().size());
	}
}
