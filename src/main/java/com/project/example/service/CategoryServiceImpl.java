package com.project.example.service;

import com.project.example.exceptions.APIException;
import com.project.example.exceptions.ResourceNotFoundException;
import com.project.example.model.Category;
import com.project.example.payload.CategoryDTO;
import com.project.example.payload.CategoryResponse;
import com.project.example.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper modelMapper;

       @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

           Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                   ?Sort.by(sortBy).ascending()
                   :Sort.by(sortBy).descending();

           Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
           Page<Category> pageCategories = categoryRepository.findAll(pageDetails);

           List<Category> categories =  pageCategories.getContent();
           if(categories.isEmpty()){
                throw new APIException("No categories found");
            }
            List<CategoryDTO> categoryDTOS = categories.stream()
                    .map(category -> modelMapper.map(category,CategoryDTO.class))
                    .toList();
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setContent(categoryDTOS);
            categoryResponse.setPageNumber(pageCategories.getNumber());
            categoryResponse.setPageSize(pageCategories.getSize());
            categoryResponse.setTotalElements(pageCategories.getTotalElements());
            categoryResponse.setTotalPages(pageCategories.getTotalPages());
            categoryResponse.setLastPage(pageCategories.isLast());
            return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
           Category category = modelMapper.map(categoryDTO,Category.class);
           Category category1 = categoryRepository.findByCategoryName(category.getCategoryName());
           if(category1 != null){
               throw new APIException("Category with the name "+ category.getCategoryName() +" already exist");
           }
           Category savedCategory = categoryRepository.save(category);
           CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
           return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        Category category = optionalCategory
                .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory= categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category category = modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }
}
