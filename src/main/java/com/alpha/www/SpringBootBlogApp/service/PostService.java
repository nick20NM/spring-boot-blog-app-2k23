package com.alpha.www.SpringBootBlogApp.service;

import java.util.List;

import com.alpha.www.SpringBootBlogApp.dto.PostDto;
import com.alpha.www.SpringBootBlogApp.dto.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(long id);
	
	PostDto updatePost(PostDto postDto, long id);
	
	void deletePostById(long id);
}
