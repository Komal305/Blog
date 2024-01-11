package com.blog.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.project.entities.Category;

public interface CategoryRepo  extends JpaRepository<Category, Integer>{

}
