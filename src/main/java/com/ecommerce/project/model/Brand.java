package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @NotBlank
    @Size(min=2,message = "brand must contain atleast 2 characters")
    private String brandName;

    @ManyToMany
    @JoinTable(
            name = "brand_category",
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "brand",cascade = CascadeType.ALL,orphanRemoval = true)
    List<Product> products = new ArrayList<>();

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;
        Brand brand = (Brand) o;
        return Objects.equals(brandId, brand.brandId) &&
                Objects.equals(brandName, brand.brandName);
        // KHÔNG sử dụng categories trong equals
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, brandName);
        // KHÔNG sử dụng categories trong hashCode
    }



}
