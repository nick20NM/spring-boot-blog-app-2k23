package com.alpha.www.SpringBootBlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.www.SpringBootBlogApp.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
