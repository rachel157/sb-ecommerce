package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updatedProductImage(Long productId, MultipartFile image) throws IOException;

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder,String categoryName, String keyword,Double minPrice,Double maxPrice);

    ProductResponse searchByCategoryName(String categoryName, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO getProductById(Long productId);
}
