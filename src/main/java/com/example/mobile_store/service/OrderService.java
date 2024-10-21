package com.example.mobile_store.service;

import com.example.mobile_store.entity.*;
import com.example.mobile_store.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserRepository userRepository;


    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, CartRepository cartRepository, CartDetailRepository cartDetailRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userRepository = userRepository;
    }

    //checkout
    public void addOrder(int userId) {
        //get user
        User user = userRepository.findById(userId).get();

        //get cart
        Cart cart = cartRepository.findByUser(user);

        //check if cart is null
        if (cart == null) {
            return;
        }

        //create order
        Order order = new Order();
        order.setUser(user);
        order.setTotal(cart.getTotal());
        order.setStatus("pending");
        orderRepository.save(order);

        //create order detail
        List<CartDetail> cartDetails = cartDetailRepository.findAllByCart(cart);
        for (CartDetail cartDetail : cartDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setProductName(cartDetail.getProductName());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getProduct().getPrice());
            orderDetailRepository.save(orderDetail);
        }

        //delete cart
        cartDetailRepository.deleteAll(cartDetails);
        cartRepository.delete(cart);
    }


}
