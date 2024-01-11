package com.blog.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.CategoryDto;
import com.blog.project.services.CategoryService;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//______________________________________
	@PostMapping("/createCategory")
	private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto )
	{
		CategoryDto createCategoryDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategoryDto, HttpStatus.CREATED);
	}
	//_____________________________________
	@DeleteMapping("/deleteCategoryById/{CategoryId}")
	private ResponseEntity<ApiResponse> deleteCategory( @PathVariable("CategoryId") Integer CategoryId){
         this.categoryService.deleteCategory(CategoryId);	
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted"), HttpStatus.OK);
		
	}
	
	//_____________________________________________
	@PutMapping("/updateCategory/{CategoryId}")
	private ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("CategoryId") Integer CategoryId){
		CategoryDto updateCategoryDto=this.categoryService.updateCategory(categoryDto, CategoryId);
		
		return new ResponseEntity<>(updateCategoryDto, HttpStatus.ACCEPTED);
		
	}
	//____________________________________________
	@GetMapping("/getAllCategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
	List<CategoryDto> getAllCategory =this.categoryService.getAllCategory();
		return ResponseEntity.ok(getAllCategory);
	}
	
	//_____________________________________________
	@GetMapping("/getCategoryById/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer CategoryId){
	CategoryDto getCategory =this.categoryService.getCategoryById(CategoryId);
		return ResponseEntity.ok(getCategory);
	}
	//_________________________________________________
	
	
	
}
