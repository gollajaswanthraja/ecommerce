package com.project.example.ai.service;

import com.project.example.ai.dto.ProductFilter;
import com.project.example.model.Product;

import java.util.List;

public interface ProductSearchService {

    List<Product> search(ProductFilter filter);

}