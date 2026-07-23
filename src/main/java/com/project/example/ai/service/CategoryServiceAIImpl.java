package com.project.example.ai.service;

import com.project.example.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceAIImpl implements CategoryAIService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceAIImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> category.getCategoryName().toLowerCase())
                .toList();

    }
}