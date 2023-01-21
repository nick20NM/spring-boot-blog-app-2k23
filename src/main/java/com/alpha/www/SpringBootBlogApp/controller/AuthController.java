package com.alpha.www.SpringBootBlogApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.www.SpringBootBlogApp.payload.LoginDto;
import com.alpha.www.SpringBootBlogApp.payload.RegisterDto;
import com.alpha.www.SpringBootBlogApp.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	// login/signin REST API
	@PostMapping(value = {"/login", "/signin"})
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
		String response = authService.login(loginDto);
		return ResponseEntity.ok(response);
	}
	
	// register/signup REST API
	@PostMapping(value = {"/register", "/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
		String response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
