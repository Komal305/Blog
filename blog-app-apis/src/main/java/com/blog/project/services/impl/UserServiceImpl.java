
package com.blog.project.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.project.config.AppConstants;
import com.blog.project.entities.Role;
import com.blog.project.entities.User;
import com.blog.project.exceptions.ResourceNotFoundException;
import com.blog.project.payloads.UserDto;
import com.blog.project.repositories.RoleRepository;
import com.blog.project.repositories.UserRepo;
import com.blog.project.services.UserService;

@Service
public class UserServiceImpl implements UserService {

		@Autowired
		private UserRepo userRepo;
		
		@Autowired
		private ModelMapper modelMapper;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		@Autowired
		private RoleRepository roleRepository;
		@Override
		public UserDto createUser(UserDto userDto) {
	      User user=this.dtoToUser(userDto);
	      User savedUser=this.userRepo.save(user);
			return this.userToDto(savedUser);
		}

		@Override
		public UserDto upateUser(UserDto userDto, Integer userId) 
		{
				User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));	
					user.setName(userDto.getName());
					user.setEmail(userDto.getEmail());
					user.setPassword(userDto.getPassword());
					user.setAbout(userDto.getAbout());	
					
					User updateUser=this.userRepo.save(user);
					UserDto userDto1=this.userToDto(updateUser);
					
			return userDto1;
		}

		@Override
		public UserDto getUserById(Integer userId) {
	 User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
			return this.userToDto(user);
		}

		@Override
		public List<UserDto> getAllUsers() {
			List<User> users=this.userRepo.findAll();
			List<UserDto> userDtos=users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
			return userDtos;
		}

		@Override
		public void deleteUser(Integer userId) {
			
			User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
			this.userRepo.delete(user);
		}
		
		private User dtoToUser(UserDto userDto) {
			User user=this.modelMapper.map(userDto, User.class);
			
			
			
//			user.setId(userDto.getId());
//			user.setName(userDto.getName());
//			user.setEmail(userDto.getEmail());
//			user.setPassword(userDto.getPassword());
//			user.setAbout(userDto.getAbout());
			return user;
			
		}
		
		public UserDto userToDto(User user) {
			UserDto userDto=this.modelMapper.map(user, UserDto.class);
			
//			userDto.setId(user.getId());
//			userDto.setName(user.getName());
//			userDto.setEmail(user.getEmail());
//			userDto.setPassword(user.getPassword());
//			userDto.setAbout(user.getAbout());
			return userDto;
		}

		@Override
		public UserDto registerNewUser(UserDto userDto) {
			User map = this.modelMapper.map(userDto, User.class);
			map.setPassword(this.passwordEncoder.encode(map.getPassword()));
			Role role=this.roleRepository.findById(AppConstants.ROLE_NORMAL).get();
			map.getRoles().add(role);
			User newUser= this.userRepo.save(map);
			return this.modelMapper.map(newUser, UserDto.class);
		}

	}


