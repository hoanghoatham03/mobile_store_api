package com.example.mobile_store.controller;


import com.example.mobile_store.dto.*;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.exception.ImageRequiredException;
import com.example.mobile_store.exception.NotFoundException;
import com.example.mobile_store.service.ProductService;
import com.example.mobile_store.service.UploadService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @ModelAttribute ProductCreateDTO productCreateDTO,
            @Valid @RequestParam("image") MultipartFile file) {
        if (file.isEmpty()){
            throw new ImageRequiredException("Image is required");
        }

        ProductDTO createdProduct = productService.createProduct(productCreateDTO, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }


    //get all products paginated
    @GetMapping
    public ResponseEntity<?> getAllProducts(@ModelAttribute PaginationDTO paginationDTO) {
        Pageable pageable = PageRequest.of(paginationDTO.getPageNo()-1, paginationDTO.getPageSize());
        List<ProductDTO> products = productService.getAllProducts(pageable);

        if (products.isEmpty()){
            throw new NotFoundException("Not found any product");
        }

        return ResponseEntity.ok(products);
    }


    //view product by id
    @GetMapping("/{id}")
    public ResponseEntity<?> viewProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    //update product
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @Valid @ModelAttribute ProductUpdateDTO productUpdateDTO,
                                           @RequestParam(value = "image", required = false) MultipartFile file) {
        ProductDTO updatedProduct = productService.updateProduct( productUpdateDTO, file, id);
        return ResponseEntity.ok(updatedProduct);
    }

    //delete product
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    //filter products
    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(@ModelAttribute PaginationDTO paginationDTO, @ModelAttribute ProductCriteriaDTO productCriteriaDTO) {
        Pageable pageable = PageRequest.of(paginationDTO.getPageNo()-1, paginationDTO.getPageSize());
        return ResponseEntity.ok(productService.filterProducts(pageable, productCriteriaDTO));

    }

}
