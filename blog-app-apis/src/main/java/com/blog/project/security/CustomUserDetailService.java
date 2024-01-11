package com.blog.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.project.entities.User;
import com.blog.project.exceptions.ResourceNotFoundException;
import com.blog.project.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
@Autowired
	private UserRepo userRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user from database
		
		User user=this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("username", "username"+username, 0));
		return user;
	}

}
