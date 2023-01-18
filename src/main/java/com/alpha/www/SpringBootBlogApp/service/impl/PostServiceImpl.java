package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.dto.PostDto;
import com.alpha.www.SpringBootBlogApp.dto.PostResponse;
import com.alpha.www.SpringBootBlogApp.entity.Post;
import com.alpha.www.SpringBootBlogApp.exception.ResourceNotFoundException;
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
	public PostResponse getAllPosts(int pageNo, int pageSize) {
		
		// create Pageable instance/object
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Post> pageObj = postRepository.findAll(pageable);
		
		// get content from Page object
		List<Post> posts = pageObj.getContent();
		
		List<PostDto> content = posts.stream().map(post -> entityToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(pageObj.getNumber());
		postResponse.setPageSize(pageObj.getSize());
		postResponse.setTotalElements(pageObj.getTotalElements());
		postResponse.setTotalPages(pageObj.getTotalPages());
		postResponse.setLast(pageObj.isLast());
		
		return postResponse;
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

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		return entityToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepository.save(post);
		return entityToDto(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

}
