package com.bibhu.Customer_API.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bibhu.Customer_API.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public Page<Order> findByEmail(String email, PageRequest pageRequest);
    public Optional<Order> findById(Long id);
}

