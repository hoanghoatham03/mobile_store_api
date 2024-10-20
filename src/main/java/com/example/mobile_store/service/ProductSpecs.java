package com.example.mobile_store.service;

import com.example.mobile_store.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecs{

    public static Specification<Product> productNameContains(String productName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("productName"), "%" + productName + "%");
    }

    public static Specification<Product> productPriceLessThan(double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), price);
    }

    public static Specification<Product> productPriceGreaterThan(double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<Product> productCategoryEquals(List<String> categories) {
        return (root, query, criteriaBuilder) ->
                root.get("category").in(categories);
    }

    public static Specification<Product> productManufactureEquals(List<String> manufactures) {
        return (root, query, criteriaBuilder) ->
                root.get("manufacture").in(manufactures);
    }

    public static Specification<Product> productPriceBetween(double minPrice, double maxPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
    }

}
