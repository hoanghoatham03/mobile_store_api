package com.example.mobile_store.service;

import com.example.mobile_store.dto.OrderDTO;
import com.example.mobile_store.entity.*;
import com.example.mobile_store.exception.NotFoundException;
import com.example.mobile_store.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
            throw new NotFoundException("Cart is empty");
        }

        //create order
        Order order = new Order();
        order.setUser(user);
        order.setTotal(cart.getTotal());
        order.setStatus("PENDING");
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

    //get all orders for Admin
    public List<OrderDTO> getAllOrders() {
        List<Order> orders =  orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new NotFoundException("No orders found");
        }

        return orders.stream().map(order ->
                new OrderDTO(order.getId(), order.getOrderDate(), order.getTotal(), order.getStatus(), order.getUser().getId())).toList();
    }

    //get order for user
    public OrderDTO getOrder(int orderId, int userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));

        if (order.getUser().getId() != userId) {
            return null;
        }

        return new OrderDTO(order.getId(), order.getOrderDate(), order.getTotal(), order.getStatus(), order.getUser().getId());
    }

    //update order status
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));
        order.setStatus(status);
        orderRepository.save(order);
    }


}
