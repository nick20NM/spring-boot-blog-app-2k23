package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
		Post post=dtoToEntity(postDtoReq);
		
		Post savedPost = postRepository.save(post);
		
		// convert entity to DTO
		PostDto postDtoRes=entityToDto(savedPost);
		return postDtoRes;
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> posts = postRepository.findAll();
		return posts.stream().map(post -> entityToDto(post)).collect(Collectors.toList());
	}
	
	// converting entity to DTO
	private PostDto entityToDto(Post post) {
		PostDto postDto=new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		return postDto;
	}
	
	// converting DTO to entity
	private Post dtoToEntity(PostDto postDto) {
		Post post=new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return post;
	}

}
