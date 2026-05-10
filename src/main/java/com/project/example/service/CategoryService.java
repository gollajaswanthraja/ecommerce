package com.project.example.service;

import com.project.example.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    String updateCategory(Category category, Long categoryId);
}
