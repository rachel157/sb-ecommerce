package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Long brandId;
    private String brandName;
    private Set<CategoryDTO> categories = new HashSet<>();
}

