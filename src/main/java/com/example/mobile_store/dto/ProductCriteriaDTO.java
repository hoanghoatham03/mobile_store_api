package com.example.mobile_store.dto;

import java.util.Optional;
import java.util.List;

public class ProductCriteriaDTO {
    private Optional <String> productName;
    private Optional<List<String>> price;
    private Optional<List<String>> category;
    private Optional<List<String>> manufacture;

    public ProductCriteriaDTO() {
    }

    public ProductCriteriaDTO(Optional<String> productName, Optional<List<String>> price, Optional<List<String>> category, Optional<List<String>> manufacture) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.manufacture = manufacture;
    }

    public Optional<String> getProductName() {
        return productName;
    }

    public void setProductName(Optional<String> productName) {
        this.productName = productName;
    }

    public Optional<List<String>> getPrice() {
        return price;
    }

    public void setPrice(Optional<List<String>> price) {
        this.price = price;
    }

    public Optional<List<String>> getCategory() {
        return category;
    }

    public void setCategory(Optional<List<String>> category) {
        this.category = category;
    }

    public Optional<List<String>> getManufacture() {
        return manufacture;
    }

    public void setManufacture(Optional<List<String>> manufacture) {
        this.manufacture = manufacture;
    }
}
