package com.example.ecom.repositories;

import com.example.ecom.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    public Optional<Order> findOrderById(int orderId);
    public Order save(Order order);
    public void deleteAll();
    List<Order> findAll();

}
