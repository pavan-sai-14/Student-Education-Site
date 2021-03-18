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
public class UserResgistrationDTO implements Serializable{
	
	private long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private com.pavansaireddy.studenteducationsystem.model.Roles role;
}
