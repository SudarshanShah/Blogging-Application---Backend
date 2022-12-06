package com.blog.apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
