package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    //List<Brand> findByCategories_CategoryId(Long categoryId);

    List<Brand> findByCategories_CategoryNameIgnoreCase(String categoryName);



    //List<Brand> findByBrandNameContainingIgnoreCase(String keyword);
}
