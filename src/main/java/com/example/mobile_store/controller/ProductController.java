package com.example.mobile_store.controller;


import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.service.ProductService;
import com.example.mobile_store.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    //create product
    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
            @RequestParam("image") MultipartFile file) {

        ProductDTO createdProduct = productService.createProduct(productDTO, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


}
