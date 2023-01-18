package com.alpha.www.SpringBootBlogApp.service;

import java.util.List;

import com.alpha.www.SpringBootBlogApp.dto.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
	
	List<CommentDto> getCommentsByPostId(long postId);
}
