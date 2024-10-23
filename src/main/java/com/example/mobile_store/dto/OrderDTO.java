package com.example.mobile_store.dto;

import com.example.mobile_store.entity.OrderDetail;
import com.example.mobile_store.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime orderDate;

    private Double total;

    private String status;

    private int user_id;

    public OrderDTO() {
    }

    public OrderDTO(Integer id, LocalDateTime orderDate, Double total, String status, int user_id) {
        this.id = id;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
        this.user_id = user_id;
    }

    // Getters and Setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
