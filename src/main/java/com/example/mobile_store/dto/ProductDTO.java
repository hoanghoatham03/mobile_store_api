package com.example.mobile_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {

    private Integer id;

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Item code is required")
    private String itemcode;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Manufacture is required")
    private String manufacture;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotBlank(message = "Product condition is required")
    private String productCondition;

    @NotBlank(message = "Image is required")
    private String image;

    public ProductDTO() {
    }

    public ProductDTO(@NotBlank(message = "Product name is required") String productName, @NotBlank(message = "Item code is required") String itemcode, @NotBlank(message = "Description is required") String description, @NotBlank(message = "Manufacture is required") String manufacture, @NotBlank(message = "Category is required") String category, @NotNull(message = "Price is required") Double price, @NotNull(message = "Quantity is required") Integer quantity, @NotBlank(message = "Product condition is required") String productCondition, @NotBlank(message = "Image is required") String image, Integer id) {
        this.productName = productName;
        this.itemcode = itemcode;
        this.description = description;
        this.manufacture = manufacture;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.productCondition = productCondition;
        this.image = image;
        this.id = id;
    }

    public @NotBlank(message = "Product name is required") String getProductName() {
        return productName;
    }

    public void setProductName(@NotBlank(message = "Product name is required") String productName) {
        this.productName = productName;
    }

    public @NotBlank(message = "Item code is required") String getItemcode() {
        return itemcode;
    }

    public void setItemcode(@NotBlank(message = "Item code is required") String itemcode) {
        this.itemcode = itemcode;
    }

    public @NotBlank(message = "Description is required") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Description is required") String description) {
        this.description = description;
    }

    public @NotBlank(message = "Manufacture is required") String getManufacture() {
        return manufacture;
    }

    public void setManufacture(@NotBlank(message = "Manufacture is required") String manufacture) {
        this.manufacture = manufacture;
    }

    public @NotBlank(message = "Category is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotBlank(message = "Category is required") String category) {
        this.category = category;
    }

    public @NotNull(message = "Price is required") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Price is required") Double price) {
        this.price = price;
    }

    public @NotNull(message = "Quantity is required") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull(message = "Quantity is required") Integer quantity) {
        this.quantity = quantity;
    }

    public @NotBlank(message = "Product condition is required") String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(@NotBlank(message = "Product condition is required") String productCondition) {
        this.productCondition = productCondition;
    }

    public @NotBlank(message = "Image is required") String getImage() {
        return image;
    }

    public void setImage(@NotBlank(message = "Image is required") String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
