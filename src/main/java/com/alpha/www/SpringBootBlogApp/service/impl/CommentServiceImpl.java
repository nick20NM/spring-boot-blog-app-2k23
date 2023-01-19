package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.entity.Comment;
import com.alpha.www.SpringBootBlogApp.entity.Post;
import com.alpha.www.SpringBootBlogApp.exception.BlogAPIException;
import com.alpha.www.SpringBootBlogApp.exception.ResourceNotFoundException;
import com.alpha.www.SpringBootBlogApp.payload.CommentDto;
import com.alpha.www.SpringBootBlogApp.repository.CommentRepository;
import com.alpha.www.SpringBootBlogApp.repository.PostRepository;
import com.alpha.www.SpringBootBlogApp.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = modelMapper.map(commentDto, Comment.class);
		
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// set post to comment entity
		comment.setPost(post);
		
		// save comment entity to DB
		Comment savedComment = commentRepository.save(comment);
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		
		// retrieve comments by postId
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		// converting list of comment entities to list of comment dto's
		return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
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
		
		return modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDtoRequest) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
		}
		
		comment.setName(commentDtoRequest.getName());
		comment.setEmail(commentDtoRequest.getEmail());
		comment.setBody(commentDtoRequest.getBody());
		
		Comment updatedComment = commentRepository.save(comment);
		return modelMapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		// retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		
		// retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		
		if (!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
		}
		
		commentRepository.delete(comment);
	}

}
