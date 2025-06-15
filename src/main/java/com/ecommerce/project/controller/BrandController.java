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

    @GetMapping("/public/category/{categoryId}/brands")
    public ResponseEntity<List<BrandDTO>> getBrandsByCategoryId(@PathVariable Long categoryId){
        List<BrandDTO> brands = brandService.getBrandsByCategoryId(categoryId);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }


}
