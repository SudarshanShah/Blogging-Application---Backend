package com.blog.apis.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {

	private int id;
	
	@NotBlank
	@Size(min = 2, message = "name must be of at least 2 characters")
	private String name;
	
	@Email(message = "your email address is not valid")
	private String email;
	
	@NotBlank
	@Size(min = 3, max = 10, message = "Password must be between 3-10 characters")
	private String password;
	
	@NotBlank
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
