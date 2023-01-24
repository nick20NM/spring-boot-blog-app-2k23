package com.alpha.www.SpringBootBlogApp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.www.SpringBootBlogApp.entity.Category;
import com.alpha.www.SpringBootBlogApp.exception.ResourceNotFoundException;
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

	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
		
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map(category -> modelMapper.map(category, CategoryDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDto updateCategory(long categoryId, CategoryDto categoryDto) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
		
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		Category updatedCategory  = categoryRepository.save(category);
		return modelMapper.map(updatedCategory, CategoryDto.class);
	}

}
