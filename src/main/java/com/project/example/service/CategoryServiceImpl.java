package com.project.example.service;

import com.project.example.exceptions.APIException;
import com.project.example.exceptions.ResourceNotFoundException;
import com.project.example.model.Category;
import com.project.example.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

       @Override
    public List<Category> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        if(categories.isEmpty()){
            throw new APIException("No categories found");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
           Category category1 = categoryRepository.findByCategoryName(category.getCategoryName());
           if(category1 != null){
               throw new APIException("Category with the name "+ category.getCategoryName() +" already exist");
           }
           categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        Category category = optionalCategory
                .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return "Category deleted successfully with categoryId "+categoryId;
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Optional<Category> optionalCategory= categoryRepository.findById(categoryId);

        Category category1= optionalCategory
                .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        category1.setCategoryName(category.getCategoryName());
        categoryRepository.save(category1);
        return "Category updated successfully with categoryId "+categoryId;
    }
}
