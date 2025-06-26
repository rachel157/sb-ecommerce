package com.ecommerce.project.controller;

import com.ecommerce.project.payload.BrandDTO;
import com.ecommerce.project.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/public/brands")
    public ResponseEntity<List<BrandDTO>> getAllBrands(){
        List<BrandDTO> brands = brandService.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryName}/brands")
    public ResponseEntity<List<BrandDTO>> getBrandsByCategoryName(@PathVariable String categoryName){
        List<BrandDTO> brands = brandService.getBrandsByCategoryName(categoryName);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }





}
