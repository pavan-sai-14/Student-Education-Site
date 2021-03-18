package com.pavansaireddy.studenteducationsystem.model;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Courses implements Comparator<Long>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="title")
	private String courseTitle;
	
	@Column(name="description")
	private String courseDescription;
	
	//private long like;
	//private long dislike;
	
	@ToString.Exclude
	@OneToOne
	private Admin adminId;
	
	@ToString.Exclude
	@ManyToMany(mappedBy = "courseRegisteredByAdmin")
	private Set<Admin> courseRegisteredByAdmins;
	
	@ToString.Exclude
	@ManyToMany(mappedBy = "courseRegisteredByStudent")
	private Set<Student> courseRegisteredByStudents;

	@Override
	public int compare(Long o1, Long o2) {
		return o1<o1?-1:o1>o2?1:0;
	}
}
