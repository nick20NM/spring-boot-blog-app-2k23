package com.alpha.www.SpringBootBlogApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.www.SpringBootBlogApp.payload.CategoryDto;
import com.alpha.www.SpringBootBlogApp.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	// add category REST API
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
	}
	
	// get category REST API
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable long id){
		CategoryDto categoryDto = categoryService.getCategory(id);
		return ResponseEntity.ok(categoryDto);
	}
	
	// get all categories REST API
	@GetMapping
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	// update category REST API
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable long id, @RequestBody CategoryDto categoryDto){
		return ResponseEntity.ok(categoryService.updateCategory(id, categoryDto));
	}
}
