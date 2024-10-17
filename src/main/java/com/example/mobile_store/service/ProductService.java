package com.example.mobile_store.service;

import com.example.mobile_store.dto.ProductCreateDTO;
import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.mapper.ProductMapper;
import com.example.mobile_store.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UploadService uploadService;

    public ProductService(ProductRepository productRepository,ProductMapper productMapper,UploadService uploadService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.uploadService = uploadService;
    }

    public boolean existsByName(String name) {
        return productRepository.existsByProductName(name);
    }

    //create product
    public ProductDTO createProduct(ProductCreateDTO productCreateDTO, MultipartFile file) {

        Product product = productMapper.toEntity(productCreateDTO);

        if (file != null && !file.isEmpty()) {
            String imagePath = uploadService.handleSaveUploadFile(file, "product");
            product.setImage(imagePath);
        }

        productRepository.save(product);

        return productMapper.toDTO(product);
    }


    //get all products
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductDTO> productDTOs = products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());


        String prefix = "http://localhost:8080/resources/images/product/";
        productDTOs.forEach(dto -> dto.setImage(prefix + dto.getImage()));

        return productDTOs;
    }




}
