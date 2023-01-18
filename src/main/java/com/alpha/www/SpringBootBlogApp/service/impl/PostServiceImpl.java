package com.alpha.www.SpringBootBlogApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.dto.PostDto;
import com.alpha.www.SpringBootBlogApp.entity.Post;
import com.alpha.www.SpringBootBlogApp.repository.PostRepository;
import com.alpha.www.SpringBootBlogApp.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	// best practice to use constructor based dependency injection
//	private PostRepository postRepository;
//
//	public PostServiceImpl(PostRepository postRepository) {
//		super();
//		this.postRepository = postRepository;
//	}

	@Override
	public PostDto createPost(PostDto postDtoReq) {
		// convert DTO to entity
		Post post=new Post();
		post.setTitle(postDtoReq.getTitle());
		post.setDescription(postDtoReq.getDescription());
		post.setContent(postDtoReq.getContent());
		
		Post savedPost = postRepository.save(post);
		
		// convert entity to DTO
		PostDto postDtoRes=new PostDto();
		postDtoRes.setId(savedPost.getId());
		postDtoRes.setTitle(savedPost.getTitle());
		postDtoRes.setDescription(savedPost.getDescription());
		postDtoRes.setContent(savedPost.getContent());
		return postDtoRes;
	}

}
