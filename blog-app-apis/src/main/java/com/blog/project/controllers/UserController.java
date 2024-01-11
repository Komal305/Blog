package com.blog.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.UserDto;
import com.blog.project.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto=this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<List<UserDto>> getAllUsers(){
	List<UserDto> get =this.userService.getAllUsers();
		return ResponseEntity.ok(get);
	}
	
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<UserDto> getUsersById(@PathVariable("userId") Integer uId){
	UserDto get =this.userService.getUserById(uId);
		return ResponseEntity.ok(get);
	}
	
	@PutMapping("/update/{userId}")
	private ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		UserDto updateUserDto=this.userService.upateUser(userDto, userId);
		
		return new ResponseEntity<>(updateUserDto, HttpStatus.OK);
		
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	private ResponseEntity<ApiResponse> deleteUser( @PathVariable("userId") Integer uId){
         this.userService.deleteUser(uId);	
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted"), HttpStatus.OK);
		
	}
	
	
	
	
}
