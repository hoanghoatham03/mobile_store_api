package com.example.mobile_store.service;

import com.example.mobile_store.dto.ProductCreateDTO;
import com.example.mobile_store.dto.ProductCriteriaDTO;
import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.dto.ProductUpdateDTO;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.mapper.ProductMapper;
import com.example.mobile_store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UploadService uploadService;

    final String IMAGE_PATH = "http://localhost:8080/resources/images/product/";
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
            product.setImage(IMAGE_PATH+imagePath);
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

        return productDTOs;

    }


    //view product by id
    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        ProductDTO productDTO = productMapper.toDTO(product);

        return productDTO;
    }

    //update product
    public ProductDTO updateProduct(ProductUpdateDTO productUpdateDTO, MultipartFile file, Integer id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return null;
        }

        if (productUpdateDTO.getProductName() != null) {
            product.setProductName(productUpdateDTO.getProductName());
        }
        if (productUpdateDTO.getItemcode() != null) {
            product.setItemcode(productUpdateDTO.getItemcode());
        }
        if (productUpdateDTO.getDescription() != null) {
            product.setDescription(productUpdateDTO.getDescription());
        }
        if (productUpdateDTO.getManufacture() != null) {
            product.setManufacture(productUpdateDTO.getManufacture());
        }
        if (productUpdateDTO.getCategory() != null) {
            product.setCategory(productUpdateDTO.getCategory());
        }
        if (productUpdateDTO.getPrice() != null) {
            product.setPrice(productUpdateDTO.getPrice());
        }
        if (productUpdateDTO.getQuantity() != null) {
            product.setQuantity(productUpdateDTO.getQuantity());
        }
        if (productUpdateDTO.getProductCondition() != null) {
            product.setProductCondition(productUpdateDTO.getProductCondition());
        }

        if (file != null && !file.isEmpty()) {
            String imagePath = uploadService.handleSaveUploadFile(file, "product");
            product.setImage(IMAGE_PATH+imagePath);
        }

        productRepository.save(product);

        return productMapper.toDTO(product);
    }


    //delete product
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

   //filter products
    public List<ProductDTO> filterProducts(Pageable pageable, ProductCriteriaDTO productCriteriaDTO) {
        if (productCriteriaDTO.getProductName() == null && productCriteriaDTO.getPrice() == null &&
                productCriteriaDTO.getCategory() == null && productCriteriaDTO.getManufacture() == null) {
            return getAllProducts(pageable);
        }

        Specification<Product> combinedSpec = Specification.where(null);

        if (productCriteriaDTO.getProductName() != null && productCriteriaDTO.getProductName().isPresent()) {
            combinedSpec = combinedSpec.and(ProductSpecs.productNameContains(productCriteriaDTO.getProductName().get()));
        }

        if (productCriteriaDTO.getPrice() != null && productCriteriaDTO.getPrice().isPresent()) {
            List<String> priceList = productCriteriaDTO.getPrice().get();
            if (priceList.size() == 1) {
                double price = Double.parseDouble(priceList.get(0));
                if (price == 20000000) {
                    combinedSpec = combinedSpec.and(ProductSpecs.productPriceGreaterThan(price));
                }
                if (price == 5000000) {
                    combinedSpec = combinedSpec.and(ProductSpecs.productPriceLessThan(price));
                }

            } else if (priceList.size() == 2) {
                double minPrice = Double.parseDouble(priceList.get(0));
                double maxPrice = Double.parseDouble(priceList.get(1));
                combinedSpec = combinedSpec.and(ProductSpecs.productPriceBetween(minPrice, maxPrice));
            }
        }


        if ( productCriteriaDTO.getCategory() != null && productCriteriaDTO.getCategory().isPresent()) {
            combinedSpec = combinedSpec.and(ProductSpecs.productCategoryEquals(productCriteriaDTO.getCategory().get()));
        }


        if ( productCriteriaDTO.getManufacture() != null && productCriteriaDTO.getManufacture().isPresent()) {
            combinedSpec = combinedSpec.and(ProductSpecs.productManufactureEquals(productCriteriaDTO.getManufacture().get()));
        }

        Page<Product> products = productRepository.findAll(combinedSpec, pageable);
        List<Product> productList = products.getContent();
        List<ProductDTO> productDTOs = productList.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        return productDTOs;

    }


}
