package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.dto.CommentDto;
import com.alpha.www.SpringBootBlogApp.entity.Comment;
import com.alpha.www.SpringBootBlogApp.entity.Post;
import com.alpha.www.SpringBootBlogApp.exception.BlogAPIException;
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

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		
		// retrieve comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		// converting list of comment entities to list of comment dto's
		return comments.stream().map(comment -> entityToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
		}
		
		return entityToDto(comment);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDtoRequest) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belongs to post");
		}
		
		comment.setName(commentDtoRequest.getName());
		comment.setEmail(commentDtoRequest.getEmail());
		comment.setBody(commentDtoRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		return entityToDto(updatedComment);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belongs to post");
		}
		
		commentRepository.delete(comment);
	}

}
