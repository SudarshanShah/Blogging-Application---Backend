package com.blog.apis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.Category;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.CategoryDto;
import com.blog.apis.repositories.CategoryRepo;
import com.blog.apis.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.mapper.map(categoryDto, Category.class);
		Category addedCategory = this.repo.save(category);
		
		return this.mapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " categoryId ", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.repo.save(category);
		
		return this.mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " categoryId ", categoryId));
		
		this.repo.delete(category);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.repo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " categoryId ", categoryId));
		
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories = this.repo.findAll();
		List<CategoryDto> categoryDtos = categories.stream()
												   .map((category) -> this.mapper.map(category, CategoryDto.class))
												   .collect(Collectors.toList());
		
		return categoryDtos;
	}

}
