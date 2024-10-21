package com.example.mobile_store.service;

import com.example.mobile_store.dto.CartDTO;
import com.example.mobile_store.entity.Cart;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.entity.User;
import com.example.mobile_store.mapper.CartMapper;
import com.example.mobile_store.repository.CartRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartMapper cartMapper;

    public CartDTO addToCart(Long userId, Integer productId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cart.setProductName(product.getProductName());
        cart.setUnitPrice(product.getPrice());
        cart.setPrice(product.getPrice() * quantity);
        cart.setAction("Added");

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart);
    }

    public List<CartDTO> getCartByUserId(Integer userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        return carts.stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CartDTO> getCart() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }
}