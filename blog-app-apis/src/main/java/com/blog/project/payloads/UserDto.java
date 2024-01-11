package com.blog.project.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private Integer id;
	
	@NotEmpty
	@Size(min=2,max=20,message="name should be min 2 and max 20 char")
	private String name ;

@Email(message="incorrect mail id")
	private String email ;

	
	@NotEmpty
	@Size(min=2,max=10,message="incorrect pattern of Password")
	private String password;
	
	@NotEmpty
	private String about ;
	
}
