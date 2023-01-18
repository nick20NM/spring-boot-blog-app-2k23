package com.alpha.www.SpringBootBlogApp.service;

import java.util.List;

import com.alpha.www.SpringBootBlogApp.dto.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	List<PostDto> getAllPosts();
	
	PostDto getPostById(long id);
	
	PostDto updatePost(PostDto postDto, long id);
}
