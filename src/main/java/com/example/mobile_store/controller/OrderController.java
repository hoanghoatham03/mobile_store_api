package com.example.mobile_store.controller;

import com.example.mobile_store.dto.OrderDTO;
import com.example.mobile_store.entity.Order;
import com.example.mobile_store.entity.User;
import com.example.mobile_store.service.OrderService;
import com.example.mobile_store.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final Set<String> ADMIN_STATUS = Set.of("PENDING", "DELIVERING", "CANCELLED", "COMPLETED");
    private final String USER_STATUS = "CANCELLED";


    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    //check current user
    private boolean isCurrentUser(int userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        int currentUserId = user.getId();
        return userId == currentUserId;
    }


    //checkout
    @PostMapping("/checkout/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> checkout(@PathVariable int userId) {
        if (!isCurrentUser(userId)) {
            return ResponseEntity.badRequest().body("You are not authorized to perform this action");
        }
        orderService.addOrder(userId);
        return ResponseEntity.ok("Order added successfully");
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
    public ResponseEntity<?> getOrderById(@PathVariable int orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        int userId = user.getId();
        return ResponseEntity.ok(orderService.getOrder(orderId, userId));
    }

    //update order status
    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId, @RequestBody String status, Authentication authentication) {
        //check user role
        User user = (User) authentication.getPrincipal();
        String role = user.getRole().getName();
        status = status.replace("\"", "").toUpperCase();
        System.out.println("Status: " + status);


        if (role.equals("USER")) {
            int userId = user.getId();
            OrderDTO order = orderService.getOrder(orderId, userId);
            if (order == null) {
                return ResponseEntity.badRequest().body("You are not authorized to perform this action");
            }
            if (!status.equals(USER_STATUS)) {
                return ResponseEntity.badRequest().body("Invalid status");
            }
        }

        if (role.equals("ADMIN") && !ADMIN_STATUS.contains(status)) {
            return ResponseEntity.badRequest().body("Invalid status");
        }


        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated successfully");


    }

}
