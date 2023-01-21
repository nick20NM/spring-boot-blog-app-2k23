package com.alpha.www.SpringBootBlogApp.service;

import com.alpha.www.SpringBootBlogApp.payload.LoginDto;

public interface AuthService {
	String login(LoginDto loginDto);
}
