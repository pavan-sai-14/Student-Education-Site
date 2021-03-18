package com.pavansaireddy.studenteducationsystem.web.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseDTO implements Serializable{
	
	private long id;
	
	private String title;
	
	private String description;
	
	private long adminId;
}
