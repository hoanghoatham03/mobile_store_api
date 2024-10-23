package com.example.mobile_store.repository;

import com.example.mobile_store.entity.Cart;
import com.example.mobile_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
    Cart findByUserId(int userId);
}
