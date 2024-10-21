package com.example.mobile_store.controller;

import com.example.mobile_store.dto.CartRequestDTO;
import com.example.mobile_store.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    //add product to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody  CartRequestDTO cartRequestDTO) {
        cartService.addToCart(cartRequestDTO.getProductId(), cartRequestDTO.getQuantity(), cartRequestDTO.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
