package com.alpha.www.SpringBootBlogApp.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.alpha.www.SpringBootBlogApp.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	// generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		
		Date currentDate = new Date();
		
		Date expiredate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
							.setSubject(username)
							.setIssuedAt(new Date())
							.setExpiration(expiredate)
							.signWith(key())
							.compact();
		
		return token;
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(
				Decoders.BASE64.decode(jwtSecret)
				);
	}
	
	// get username from Jwt token
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(key())
							.build()
							.parseClaimsJws(token)
							.getBody();
		String username = claims.getSubject();
		return username;
	}
	
	// validate Jwt token
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key())
				.build()
				.parse(token);
			return true;
		} catch (MalformedJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "unsupported JWT token");
		} catch (IllegalArgumentException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
		}
	}
}
