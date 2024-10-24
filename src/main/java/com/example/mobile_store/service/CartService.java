package com.example.mobile_store.service;

import com.example.mobile_store.dto.CartDTO;
import com.example.mobile_store.entity.Cart;
import com.example.mobile_store.entity.CartDetail;
import com.example.mobile_store.entity.Product;
import com.example.mobile_store.entity.User;
import com.example.mobile_store.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartDetailRepository cartDetailRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
    }

    //get cart
    public CartDTO getCart(int userId) {
        //get user
        User user = userRepository.findById(userId).get();

        //get cart
        Cart cart = cartRepository.findByUser(user);

        if (cart == null) {
            return null;
        }

        //get sum product
        int sumProduct = 0;
        if (cart != null) {
           //get from quantity
            sumProduct = cartDetailRepository.sumQuantityByCart(cart.getId());
        }

        return new CartDTO(cart.getId(), cart.getTotal(), user.getId(), sumProduct);
    }

    //add to cart
    public void addToCart(int productId, int quantity, int userId) {
        //get user
        User user = userRepository.findById(userId).get();

        //get cart
        Cart cart = cartRepository.findByUser(user);

        //check if cart is null
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart.setTotal(0.0);
            cartRepository.save(cart);

        }

        //get product
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            //check if product is already in cart
           Product selectedProduct = product.get();
              CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, selectedProduct);

                if (cartDetail == null) {
                    cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setAction("add");
                    cartDetail.setProduct(selectedProduct);
                    cartDetail.setProductName(selectedProduct.getProductName());
                    cartDetail.setUnitPrice(selectedProduct.getPrice());
                    cartDetail.setPrice(selectedProduct.getPrice() * quantity);
                    cartDetail.setQuantity(quantity);
                    cartDetailRepository.save(cartDetail);
                } else {
                    cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
                    cartDetail.setPrice(cartDetail.getUnitPrice() * cartDetail.getQuantity());
                    cartDetailRepository.save(cartDetail);
                }

                cart.setTotal(cart.getTotal() + (selectedProduct.getPrice() * quantity));
                cartRepository.save(cart);
        }



    }

    //remove from cart
    public void removeFromCart(int productId, int userId) {
        //get user
        User user = userRepository.findById(userId).get();

        //get cart
        Cart cart = cartRepository.findByUser(user);

        //get product
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {

            Product selectedProduct = product.get();
            CartDetail cartDetail = cartDetailRepository.findByCartAndProduct(cart, selectedProduct);

            if (cartDetail != null) {
                cartDetailRepository.delete(cartDetail);
                cart.setTotal(cart.getTotal() - cartDetail.getPrice());
            }

            cartRepository.save(cart);
        }


    }




}
