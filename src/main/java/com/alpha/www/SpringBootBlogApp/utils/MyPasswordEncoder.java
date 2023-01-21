package com.alpha.www.SpringBootBlogApp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder {
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("tom"));
		System.out.println(encoder.encode("admin"));
	}
}
