package com.ecommerce.project.payload;

import com.ecommerce.project.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> withKeywordAndCategory(String keyword, String categoryName) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("productName")),
                        "%" + keyword.toLowerCase() + "%"
                ));
            }

            if (categoryName != null && !categoryName.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("category").get("categoryName")),
                        "%" + categoryName.toLowerCase() + "%"
                ));
            }

            return predicates.isEmpty() ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
