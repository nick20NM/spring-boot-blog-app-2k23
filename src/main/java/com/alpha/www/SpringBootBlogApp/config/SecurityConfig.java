package com.alpha.www.SpringBootBlogApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alpha.www.SpringBootBlogApp.security.JwtAuthenticationEntryPoint;
import com.alpha.www.SpringBootBlogApp.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity.csrf().disable()
					.authorizeHttpRequests(authorize -> 
			//		authorize.anyRequest().authenticated()
					authorize
					.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//					.requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
					.requestMatchers("/api/v1/auth/**").permitAll()
					.anyRequest().authenticated())
					.exceptionHandling(exception -> 
					exception.authenticationEntryPoint(authenticationEntryPoint))
					.sessionManagement(session -> 
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return httpSecurity.build();
	}
	
//	@Bean
//	UserDetailsService userDetailsService() {
//		UserDetails tom = User.builder()
//				.username("tom")
//				.password(passwordEncoder().encode("tom"))
//				.roles("USER")
//				.build();
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(tom, admin);
//	}
}
