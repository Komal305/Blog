package com.blog.project;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.project.config.AppConstants;
import com.blog.project.entities.Role;
import com.blog.project.repositories.RoleRepository;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = { "com.blog.project.repositories", "com.blog.project.services.impl",
		"com.blog.project.services", "com.blog.project.config" })
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("encode password" + this.passwordEncoder.encode("komal"));
		try {
			Role role=new Role();
			role.setId(AppConstants.ROLE_ADMIN);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(AppConstants.ROLE_NORMAL);
			role1.setName("NORMAL_USER");
			
			List<Role> roles=List.of(role,role1);
			List<Role> result=this.roleRepository.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
