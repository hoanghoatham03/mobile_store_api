package com.example.mobile_store.dto;

import com.example.mobile_store.entity.Cart;
import com.example.mobile_store.entity.Role;
import jakarta.persistence.OneToOne;

public class UserDTO {
    private Integer id;
    private String username;
    private Role role;


    private Cart cart;


    public UserDTO() {
    }

    public UserDTO(Integer id, String username, Role role, Cart cart) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.cart = cart;
    }

}
