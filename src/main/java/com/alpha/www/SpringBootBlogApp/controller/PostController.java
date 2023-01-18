package com.alpha.www.SpringBootBlogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.www.SpringBootBlogApp.dto.PostDto;
import com.alpha.www.SpringBootBlogApp.dto.PostResponse;
import com.alpha.www.SpringBootBlogApp.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	// create post rest api
	@PostMapping("/")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	// get all posts rest api
//	@GetMapping("/")
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo, 
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize, 
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy
			){
		return postService.getAllPosts(pageNo, pageSize, sortBy);
	}
	
	// get post by id rest api
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	// update post by id rest api
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable long id){
		PostDto postDtoResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
	}
	
	// delete post rest api
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id){
		postService.deletePostById(id);
		return ResponseEntity.ok("post deleted successfully");
	}
}
