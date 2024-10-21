package com.example.mobile_store.controller;

import com.example.mobile_store.dto.CartAddDTO;
import com.example.mobile_store.dto.CartDTO;
import com.example.mobile_store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

//    @PostMapping("/add")
//    public ResponseEntity<CartDTO> addToCart(@RequestParam Long userId,
//                                             @RequestParam Integer productId,
//                                             @RequestParam Integer quantity) {
//        CartDTO cartDTO = cartService.addToCart(userId, productId, quantity);
//        return ResponseEntity.ok(cartDTO);
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<CartDTO>> getCartByUserId(@PathVariable Integer userId) {
//        List<CartDTO> cartItems = cartService.getCartByUserId(userId);
//        return ResponseEntity.ok(cartItems);
//    }
    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartAddDTO request) {
        CartDTO cartDTO = cartService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartDTO>> getCartByUserId(@PathVariable Integer userId) {
        List<CartDTO> cartItems = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartItems);
        }
    @GetMapping
    public ResponseEntity<List<CartDTO>> getCart() {
        List<CartDTO> cartItems = cartService.getCart();
        return ResponseEntity.ok(cartItems);
    }

}