package com.alpha.www.SpringBootBlogApp.service;

import com.alpha.www.SpringBootBlogApp.dto.CommentDto;

public interface CommentService {

	CommentDto createComment(long postId, CommentDto commentDto);
}
