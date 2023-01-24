package com.alpha.www.SpringBootBlogApp.service;

import java.util.List;

import com.alpha.www.SpringBootBlogApp.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);
	
	CategoryDto getCategory(Long categoryId);
	
	List<CategoryDto> getAllCategories();
}
