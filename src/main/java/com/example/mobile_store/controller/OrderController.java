package com.example.mobile_store.controller;

import com.example.mobile_store.entity.User;
import com.example.mobile_store.service.OrderService;
import com.example.mobile_store.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }


    //checkout
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<?> checkout(@PathVariable int userId) {
        orderService.addOrder(userId);
        return ResponseEntity.ok().build();
    }


    //get all orders
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    //get order by order id
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getOrderById(@PathVariable int orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        int userId = user.getId();
        return ResponseEntity.ok(orderService.getOrders(userId, orderId));
    }


}
