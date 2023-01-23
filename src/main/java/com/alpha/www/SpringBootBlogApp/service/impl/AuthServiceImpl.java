package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.entity.Role;
import com.alpha.www.SpringBootBlogApp.entity.User;
import com.alpha.www.SpringBootBlogApp.exception.BlogAPIException;
import com.alpha.www.SpringBootBlogApp.payload.LoginDto;
import com.alpha.www.SpringBootBlogApp.payload.RegisterDto;
import com.alpha.www.SpringBootBlogApp.repository.RoleRepository;
import com.alpha.www.SpringBootBlogApp.repository.UserRepository;
import com.alpha.www.SpringBootBlogApp.security.JwtTokenProvider;
import com.alpha.www.SpringBootBlogApp.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	public String login(LoginDto loginDto) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(), 
				loginDto.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {

		// check if username exists in database
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "username already exists");
		}
		
		// check if email exists in database
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "email already exists");
		}
		
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "user registered successfully";
	}

}
