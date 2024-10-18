package com.example.mobile_store.service;

import com.example.mobile_store.dto.ProductCreateDTO;
import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.mapper.ProductMapper;
import com.example.mobile_store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    //get all products paginated
    public List<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = (Page<Product>) productRepository.findAll(pageable);

        List<Product> productList = products.getContent();

        List<ProductDTO> productDTOs = productList.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        String prefix = "http://localhost:8080/resources/images/product/";
        productDTOs.forEach(dto -> dto.setImage(prefix + dto.getImage()));

        return productDTOs;

    }


    //view product by id
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = productMapper.toDTO(product);
        productDTO.setImage("http://localhost:8080/resources/images/product/" + productDTO.getImage());

        return productDTO;
    }

    //delete product
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
