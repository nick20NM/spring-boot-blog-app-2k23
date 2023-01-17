package com.alpha.www.SpringBootBlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.www.SpringBootBlogApp.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
