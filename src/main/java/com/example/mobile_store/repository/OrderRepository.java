package com.example.mobile_store.repository;

import com.example.mobile_store.entity.Order;
import com.example.mobile_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
   //find order by userId
    Order findByUserId(int userId);
}
