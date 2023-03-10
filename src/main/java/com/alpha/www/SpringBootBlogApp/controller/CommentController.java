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
import org.springframework.web.bind.annotation.RestController;

import com.alpha.www.SpringBootBlogApp.payload.CommentDto;
import com.alpha.www.SpringBootBlogApp.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createcomment(
			@PathVariable long postId, 
			@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable long postId){
		List<CommentDto> comments = commentService.getCommentsByPostId(postId);
		return ResponseEntity.ok(comments);
	}
	
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(
			@PathVariable long postId, 
			@PathVariable long commentId){
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return ResponseEntity.ok(commentDto);
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComment(
			@PathVariable long postId, 
			@PathVariable long commentId, 
			@Valid @RequestBody CommentDto commentDto){
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
		return ResponseEntity.ok(updatedComment);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(
			@PathVariable long postId, 
			@PathVariable long commentId){
		commentService.deleteComment(postId, commentId);
		return ResponseEntity.ok("comment deleted successfully");
	}
}
