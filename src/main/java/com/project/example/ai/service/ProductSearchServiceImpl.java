package com.project.example.ai.service;

import com.project.example.ai.dto.ProductFilter;
import com.project.example.ai.specification.ProductSpecification;
import com.project.example.model.Product;
import com.project.example.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceImpl
        implements ProductSearchService {

    private final ProductRepository productRepository;

    public ProductSearchServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;

    }

    @Override
    public List<Product> search(ProductFilter filter) {

        return productRepository.findAll(

                ProductSpecification.filter(filter)

        );

    }

}