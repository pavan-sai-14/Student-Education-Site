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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="student",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Comparator<Long>{
	
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
	
	@ToString.Exclude
	@ManyToMany()
	@JoinTable(
			name="student_courses",
			joinColumns = @JoinColumn(name="student_id"),
			inverseJoinColumns = @JoinColumn(name="courses_id")
			)
	private Set<Courses> courseRegisteredByStudent;

	@Override
	public int compare(Long o1, Long o2) {
		return o1<o1?-1:o1>o2?1:0;
	}
	
	public void registerCourse(Courses c)
	{
		this.getCourseRegisteredByStudent().add(c);
		this.setTotalRegisteredCourses(this.getCourseRegisteredByStudent().size());
	}
	
	public void unRegisterCourse(Courses c)
	{
		this.getCourseRegisteredByStudent().remove(c);
		this.setTotalRegisteredCourses(this.getCourseRegisteredByStudent().size());
	}
}
