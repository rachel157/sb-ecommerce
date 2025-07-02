package com.ecommerce.project.controller;

import com.ecommerce.project.payload.BrandDTO;
import com.ecommerce.project.service.BrandService;
import jakarta.validation.Valid;
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

    @PostMapping("/categories/{categoryId}/brand")
    public ResponseEntity<BrandDTO> createBrand(@PathVariable Long categoryId, @Valid @RequestBody BrandDTO brandDTO){
        BrandDTO savedBrand = brandService.createBrand(categoryId,brandDTO);
        return new ResponseEntity<>(savedBrand, HttpStatus.OK);
    }

    @PutMapping("/admin/brands/{brandId}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long brandId,@Valid @RequestBody BrandDTO brandDTO){
        BrandDTO updatedBrand = brandService.updateBrand(brandId,brandDTO);
        return new ResponseEntity<>(updatedBrand,HttpStatus.OK);
    }

    @DeleteMapping("/admin/brands/{brandId}")
    public ResponseEntity<String> deleteBrand(@PathVariable  Long brandId){
        String status = brandService.deleteBrand(brandId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/public/brands/search")
    public ResponseEntity<List<BrandDTO>> searchBrandsByKeyword(@RequestParam(name = "keyword") String keyword){
        List<BrandDTO> brands = brandService.searchBrandByKeyword(keyword);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }


}
