package com.project.example.ai.dto;

import lombok.Data;

@Data
public class ProductFilter {

    private String category;

    private String brand;

    private Double maxPrice;

    private String keywords;

}