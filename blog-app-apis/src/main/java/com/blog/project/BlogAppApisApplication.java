package com.blog.project;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"com.blog.project.repositories", "com.blog.project.services.impl","com.blog.project.services","com.blog.project.config"})
public class BlogAppApisApplication implements CommandLineRunner {
@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
public ModelMapper modelMapper() {
	return new ModelMapper();
}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("encode password"+ passwordEncoder.encode("komal"));
	}
	
}