package com.alpha.www.SpringBootBlogApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.entity.Category;
import com.alpha.www.SpringBootBlogApp.payload.CategoryDto;
import com.alpha.www.SpringBootBlogApp.repository.CategoryRepository;
import com.alpha.www.SpringBootBlogApp.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category savedCategory = categoryRepository.save(category);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

}
