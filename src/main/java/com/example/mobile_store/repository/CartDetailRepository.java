package com.example.mobile_store.repository;


import com.example.mobile_store.entity.Cart;
import com.example.mobile_store.entity.CartDetail;
import com.example.mobile_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

    boolean existsByCartAndProduct(Cart cart, Product product);


    CartDetail findByCartAndProduct(Cart cart, Product product);

    List<CartDetail> findAllByCart(Cart cart);
}
