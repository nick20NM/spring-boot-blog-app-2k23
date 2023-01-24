package com.alpha.www.SpringBootBlogApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alpha.www.SpringBootBlogApp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
