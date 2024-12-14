package com.microshophub.orderservice.repository;

import org.springframework.stereotype.Repository;
import com.microshophub.orderservice.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
