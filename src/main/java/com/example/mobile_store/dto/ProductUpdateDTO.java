package com.example.mobile_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductUpdateDTO {
    private String productName;

    private String itemcode;

    private String description;

    private String manufacture;

    private String category;

    private Double price;

    private Integer quantity;

    private String productCondition;

    public ProductUpdateDTO() {
    }

    public ProductUpdateDTO(@NotBlank(message = "Product name is required") String productName, @NotBlank(message = "Item code is required") String itemcode, @NotBlank(message = "Description is required") String description, @NotBlank(message = "Manufacture is required") String manufacture, @NotBlank(message = "Category is required") String category, @NotNull(message = "Price is required") Double price, @NotNull(message = "Quantity is required") Integer quantity, @NotBlank(message = "Product condition is required") String productCondition) {
        this.productName = productName;
        this.itemcode = itemcode;
        this.description = description;
        this.manufacture = manufacture;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.productCondition = productCondition;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition;
    }
}
