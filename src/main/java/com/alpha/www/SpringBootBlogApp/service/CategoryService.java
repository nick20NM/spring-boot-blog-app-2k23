package com.alpha.www.SpringBootBlogApp.service;

import com.alpha.www.SpringBootBlogApp.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Long categoryId);
}
