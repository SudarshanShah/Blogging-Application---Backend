package com.blog.apis.services;

import java.util.List;

import com.blog.apis.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	void deleteCategory(Integer categoryId);
	
	CategoryDto getCategory(Integer categoryId);
	
	List<CategoryDto> getCategories();
}
