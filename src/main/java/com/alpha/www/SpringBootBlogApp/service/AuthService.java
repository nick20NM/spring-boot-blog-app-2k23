package com.alpha.www.SpringBootBlogApp.service;

import com.alpha.www.SpringBootBlogApp.payload.LoginDto;
import com.alpha.www.SpringBootBlogApp.payload.RegisterDto;

public interface AuthService {
	String login(LoginDto loginDto);
	
	String register(RegisterDto registerDto);
}
