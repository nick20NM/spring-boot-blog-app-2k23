package com.alpha.www.SpringBootBlogApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.dto.CommentDto;
import com.alpha.www.SpringBootBlogApp.entity.Comment;
import com.alpha.www.SpringBootBlogApp.entity.Post;
import com.alpha.www.SpringBootBlogApp.exception.ResourceNotFoundException;
import com.alpha.www.SpringBootBlogApp.repository.CommentRepository;
import com.alpha.www.SpringBootBlogApp.repository.PostRepository;
import com.alpha.www.SpringBootBlogApp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = dtoToEntity(commentDto);
		
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// set post to comment entity
		comment.setPost(post);
		
		// save comment entity to DB
		Comment savedComment = commentRepository.save(comment);
		return entityToDto(savedComment);
	}
	
	// converting entity to DTO
	private CommentDto entityToDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		return commentDto;
	}
	
	// converting DTO to entity
	private Comment dtoToEntity(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
	}

}
