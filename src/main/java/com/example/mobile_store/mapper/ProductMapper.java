package com.example.mobile_store.mapper;

import com.example.mobile_store.dto.ProductCreateDTO;
import com.example.mobile_store.dto.ProductDTO;
import com.example.mobile_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //@Mapping(target = "image", source = "image")
    ProductDTO toDTO(Product product);

    Product toEntity(ProductCreateDTO productCreateDTO);
}
