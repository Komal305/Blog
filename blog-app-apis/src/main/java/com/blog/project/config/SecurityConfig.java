package com.blog.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.project.security.CustomUserDetailService;
import com.blog.project.security.JwtAuthentionEntryPoint;
import com.blog.project.security.JwtAuthentionFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	public static final String[] PUBLIC_URLS= {"v3/api-docs",
            "v2/api-docs",
            "/auth/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"};

	
	
@Autowired
	private CustomUserDetailService customUserDetailService;
@Autowired
private JwtAuthentionEntryPoint jwtAuthentionEntryPoint; 

@Autowired
private JwtAuthentionFilter jwtAuthentionFilter;
   

@Bean
public SecurityFilterChain securityfilterChain(HttpSecurity http) throws Exception {

http
    .csrf(csrf -> csrf.disable())
    .cors(cors->cors.disable())
       .authorizeHttpRequests((authz) -> authz.requestMatchers(PUBLIC_URLS).permitAll()
    		   .requestMatchers(HttpMethod.GET).permitAll()
    		   .anyRequest().authenticated())
        .exceptionHandling(ex-> ex.authenticationEntryPoint(this.jwtAuthentionEntryPoint))
        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
   
    http.addFilterBefore(this.jwtAuthentionFilter, UsernamePasswordAuthenticationFilter.class);
    http.authenticationProvider(daoAuthenticationProvider());
  DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
    return defaultSecurityFilterChain;
}


private DaoAuthenticationProvider daoAuthenticationProvider() {
	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
}

@Bean
public BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
}

@Bean
public AuthenticationManager authenticationManager() {
    return new ProviderManager(daoAuthenticationProvider());
}
	
}
