package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Brand;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.BrandDTO;
import com.ecommerce.project.repositories.BrandRepository;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<BrandDTO> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        if(brands.isEmpty()){
            throw new APIException("No Brands Found");
        }
        return brands.stream().map(b -> modelMapper.map(b, BrandDTO.class)).toList();
    }

    @Override
    public List<BrandDTO> getBrandsByCategoryName(String categoryName) {
        List<Brand> brands = brandRepository.findByCategories_CategoryNameIgnoreCase(categoryName);
        if (brands.isEmpty()) {
            throw new APIException("No Brands Found With Category Name: " + categoryName);
        }
        return brands.stream().map(b -> modelMapper.map(b, BrandDTO.class)).toList();
    }

    @Override
    public BrandDTO updateBrand(Long brandId, BrandDTO brandDTO) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandDTO.getBrandId()));
        brand.setBrandName(brandDTO.getBrandName());
        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public BrandDTO createBrand(Long categoryId, BrandDTO brandDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        Set<Brand> brands = category.getBrands();
        Boolean isBrandExist = false;
        for (Brand brand : brands) {
            if (brand.getBrandName().equals(brandDTO.getBrandName())) {
                isBrandExist = true;
            }
        }
        if (!isBrandExist) {
            Brand brand = new Brand();
            brand.setBrandName(brandDTO.getBrandName());
            brand.getCategories().add(category);
            Brand savedBrand = brandRepository.save(brand);
            return modelMapper.map(savedBrand, BrandDTO.class);
        }
        else{
            throw new APIException("Brand Name Already Exists");
        }
    }

    @Override
    public String deleteBrand(Long brandId) {
        Brand savedBrand = brandRepository.findById(brandId)
                .orElseThrow(()-> new ResourceNotFoundException("Brand", "id", brandId));
        brandRepository.delete(savedBrand);
        return "Brand has been deleted";
    }

    @Override
    public List<BrandDTO> searchBrandByKeyword(String keyword) {
        List<Brand> brands = brandRepository.findByBrandNameContainingIgnoreCase(keyword);
        if(brands==null||brands.isEmpty()){
            throw new APIException("No Brands Found With Brand Name: " + keyword);
        }
        List<BrandDTO> brandDTOS = brands.stream().map(b -> modelMapper.map(b, BrandDTO.class)).toList();
        return brandDTOS;
    }
}
