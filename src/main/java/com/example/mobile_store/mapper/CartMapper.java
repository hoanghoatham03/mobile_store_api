package com.example.mobile_store.mapper;

import com.example.mobile_store.dto.CartDTO;
import com.example.mobile_store.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    CartDTO toDTO(Cart cart);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    Cart toEntity(CartDTO cartDTO);
}