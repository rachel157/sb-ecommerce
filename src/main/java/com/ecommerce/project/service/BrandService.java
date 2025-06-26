package com.ecommerce.project.service;

import com.ecommerce.project.payload.BrandDTO;

import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrands();

    List<BrandDTO> getBrandsByCategoryName(String categoryName);
}
