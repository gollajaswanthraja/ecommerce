package com.project.example.ai.specification;

import com.project.example.ai.dto.ProductFilter;
import com.project.example.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> filter(ProductFilter filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Category
            if (filter.getCategory() != null &&
                    !filter.getCategory().isBlank()) {

                predicates.add(

                        cb.equal(

                                cb.lower(root.get("category")
                                        .get("categoryName")),

                                filter.getCategory().toLowerCase()

                        )

                );

            }

            // Brand (Seller for now)
            if (filter.getBrand() != null &&
                    !filter.getBrand().isBlank()) {

                predicates.add(

                        cb.like(

                                cb.lower(root.get("productName")),

                                "%" + filter.getBrand().toLowerCase() + "%"

                        )

                );

            }

            // Price

            if (filter.getMaxPrice() != null &&
                    filter.getMaxPrice() > 0) {

                predicates.add(

                        cb.lessThanOrEqualTo(

                                root.get("price"),

                                filter.getMaxPrice()

                        )

                );

            }

            // Keyword

            if (filter.getKeywords() != null &&
                    !filter.getKeywords().isBlank()) {

                Predicate productName =

                        cb.like(

                                cb.lower(root.get("productName")),

                                "%" + filter.getKeywords().toLowerCase() + "%"

                        );

                Predicate description =

                        cb.like(

                                cb.lower(root.get("description")),

                                "%" + filter.getKeywords().toLowerCase() + "%"

                        );

                predicates.add(

                        cb.or(productName, description)

                );

            }

            return cb.and(predicates.toArray(new Predicate[0]));

        };

    }

}