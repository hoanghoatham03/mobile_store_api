package com.example.mobile_store.repository;

import com.example.mobile_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);


}
