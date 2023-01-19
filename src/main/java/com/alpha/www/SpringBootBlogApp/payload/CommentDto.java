package com.alpha.www.SpringBootBlogApp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	@NotEmpty(message = "should not be null or empty")
	private String name;
	
	@NotEmpty(message = "should not be null or empty")
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 4, message = "at least 4 characters")
	private String body;
}
