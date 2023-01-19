package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.entity.Post;
import com.alpha.www.SpringBootBlogApp.exception.ResourceNotFoundException;
import com.alpha.www.SpringBootBlogApp.payload.PostDto;
import com.alpha.www.SpringBootBlogApp.payload.PostResponse;
import com.alpha.www.SpringBootBlogApp.repository.PostRepository;
import com.alpha.www.SpringBootBlogApp.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		Post savedPost = postRepository.save(post);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : 
				Sort.by(sortBy).descending();
		
		// create Pageable instance/object
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> pageObj = postRepository.findAll(pageable);
		
		// get content from Page object
		List<Post> posts = pageObj.getContent();
		
		List<PostDto> content = posts.stream().map(post -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(pageObj.getNumber());
		postResponse.setPageSize(pageObj.getSize());
		postResponse.setTotalElements(pageObj.getTotalElements());
		postResponse.setTotalPages(pageObj.getTotalPages());
		postResponse.setLast(pageObj.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepository.save(post);
		return modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePostById(long id) {
		// get post by id from the database
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

}
