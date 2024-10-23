package com.example.mobile_store.dto;

import com.example.mobile_store.entity.CartDetail;
import com.example.mobile_store.entity.User;
import jakarta.persistence.*;

import java.util.List;

public class CartDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double total;

    private int user_id;

    private int sumProduct;

    public CartDTO() {
    }

    public CartDTO(Integer id, Double total, int user_id, int sumProduct) {
        this.id = id;
        this.total = total;
        this.user_id = user_id;
        this.sumProduct = sumProduct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSumProduct() {
        return sumProduct;
    }

    public void setSumProduct(int sumProduct) {
        this.sumProduct = sumProduct;
    }
}
