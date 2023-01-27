package com.alpha.www.SpringBootBlogApp.payload;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDtoV2 {
	private Long id;
	
	@NotEmpty
	@Size(min = 2, message = "at least 2 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 4, message = "at least 4 characters")
	private String description;
	
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
	
//	private long categoryId;
	
	private List<String> tags;
}
