package com.alpha.www.SpringBootBlogApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.www.SpringBootBlogApp.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(long postId);
}
