package com.example.mobile_store.controller;

import com.example.mobile_store.dto.CartRequestDTO;
import com.example.mobile_store.entity.User;
import com.example.mobile_store.service.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    //get cart
    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<?> getCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    //add product to cart
    @PostMapping("/add")
    @PreAuthorize("#cartRequestDTO.userId == authentication.principal.id")
    public ResponseEntity<?> addToCart(@Valid @RequestBody  CartRequestDTO cartRequestDTO) {

        cartService.addToCart(cartRequestDTO.getProductId(), cartRequestDTO.getQuantity(), cartRequestDTO.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Product added to cart successfully");
    }

    //remove product from cart
    @DeleteMapping("/remove")
    @PreAuthorize("#cartRequestDTO.userId == authentication.principal.id")
    public ResponseEntity<?> removeFromCart(@Valid @RequestBody  CartRequestDTO cartRequestDTO) {
        cartService.removeFromCart(cartRequestDTO.getProductId(), cartRequestDTO.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("Product removed from cart successfully");
    }
}
