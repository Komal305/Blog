package com.blog.project.services;

import java.util.List;


import com.blog.project.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto upateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	 List<UserDto> getAllUsers();
	 void deleteUser(Integer userId);
}
