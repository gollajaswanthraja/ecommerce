package com.project.example.ai.service;

import com.project.example.model.Category;
import com.project.example.repositories.CategoryRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryResolver {

    private final CategoryRepository categoryRepository;

    public CategoryResolver(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public String resolve(String aiCategory) {

        if (aiCategory == null || aiCategory.isBlank()) {
            return null;
        }

        aiCategory = aiCategory.trim().toLowerCase();

        List<Category> categories = categoryRepository.findAll();

        // Exact Match
        for (Category category : categories) {

            String dbCategory = category.getCategoryName().toLowerCase();

            if (dbCategory.equals(aiCategory)) {
                return dbCategory;
            }
        }

        // Contains Match
        for (Category category : categories) {

            String dbCategory = category.getCategoryName().toLowerCase();

            if (dbCategory.contains(aiCategory) ||
                    aiCategory.contains(dbCategory)) {

                return dbCategory;
            }
        }

        // Fuzzy Match
        String bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Category category : categories) {

            String dbCategory = category.getCategoryName().toLowerCase();

            int distance = LevenshteinDistance.getDefaultInstance()
                    .apply(aiCategory, dbCategory);

            if (distance < bestDistance) {
                bestDistance = distance;
                bestMatch = dbCategory;
            }
        }

        return bestDistance <= 2 ? bestMatch : null;
    }
}