package com.ecommerce.project.JPASpecifications;

import com.ecommerce.project.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> withKeywordAndCategoryAndMinPriceAndMaxPrice(String keyword, String categoryName,
                                                                Double minPrice, Double maxPrice,String brand) {
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

            // Thêm điều kiện lọc theo giá tối thiểu
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("specialPrice"), minPrice
                ));
            }

            // Thêm điều kiện lọc theo giá tối đa
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("specialPrice"), maxPrice
                ));
            }

            //Điều kiện lọc theo tên brand
            if(brand != null && !brand.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("brand").get("brandName")),
                        "%" + brand.toLowerCase() + "%"
                ));
            }

            return predicates.isEmpty() ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
