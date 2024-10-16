package com.example.mobile_store.mapper;

import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    @Mapping(target = "productCondition", source = "productCondition")
    Product toEntity(ProductDTO productDTO);
}
