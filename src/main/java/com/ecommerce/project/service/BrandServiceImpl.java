package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Brand;
import com.ecommerce.project.payload.BrandDTO;
import com.ecommerce.project.repositories.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        List<Brand> brands = brandRepository.findByCategories_CategoryName(categoryName);
        return brands.stream().map(b -> modelMapper.map(b, BrandDTO.class)).toList();
    }
}
