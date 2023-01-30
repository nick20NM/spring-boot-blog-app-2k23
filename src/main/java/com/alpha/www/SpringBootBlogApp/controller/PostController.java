package com.alpha.www.SpringBootBlogApp.controller;

import static com.alpha.www.SpringBootBlogApp.utils.AppConstants.DEFAULT_PAGE_NO;
import static com.alpha.www.SpringBootBlogApp.utils.AppConstants.DEFAULT_PAGE_SIZE;
import static com.alpha.www.SpringBootBlogApp.utils.AppConstants.DEFAULT_SORT_BY;
import static com.alpha.www.SpringBootBlogApp.utils.AppConstants.DEFAULT_SORT_DIRECTION;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.www.SpringBootBlogApp.payload.PostDto;
import com.alpha.www.SpringBootBlogApp.payload.PostDtoV2;
import com.alpha.www.SpringBootBlogApp.payload.PostResponse;
import com.alpha.www.SpringBootBlogApp.service.PostService;

import jakarta.validation.Valid;

@RestController
public class PostController {

	@Autowired
	private PostService postService;
	
	// create post rest api
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/v1/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	// get all posts rest api
	@GetMapping("/api/v1/posts")
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NO, required = false) int pageNo, 
			@RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize, 
			@RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy, 
			@RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir
			){
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	// get post by id rest api v1
	@GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=1")
	public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	// get post by id rest api v2
	@GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=2")
	public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable long id){
		PostDto postDto = postService.getPostById(id);
		
		PostDtoV2 postDtoV2 = new PostDtoV2();
		postDtoV2.setId(postDto.getId());
		postDtoV2.setTitle(postDto.getTitle());
		postDtoV2.setDescription(postDto.getDescription());
		postDtoV2.setContent(postDto.getContent());
		
		List<String> tags = new ArrayList<>();
		tags.add("java");
		tags.add("spring boot");
		tags.add("aws");
		
		postDtoV2.setTags(tags);
		
		return ResponseEntity.ok(postDtoV2);
	}
	
	// update post by id rest api
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/api/v1/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){
		PostDto postDtoResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postDtoResponse, HttpStatus.OK);
	}
	
	// delete post rest api
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/api/v1/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id){
		postService.deletePostById(id);
		return ResponseEntity.ok("post deleted successfully");
	}
	
	// get posts by category id REST API
	// http://localhost:8080/api/posts/category/3
	@GetMapping("/api/v1/posts/category/{id}")
	public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable long id){
		List<PostDto> postDto = postService.getPostsByCategory(id);
		return ResponseEntity.ok(postDto);
	}
}
