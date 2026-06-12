package com.example.ecom.repositories;

import com.example.ecom.models.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository{
    Map<Integer, Order> orders = new HashMap<>();

    // Interface methods
    public Optional<Order> findOrderById(int orderId){
        if(orders.containsKey(orderId)){
            return Optional.of(orders.get(orderId));
        }
        return Optional.empty();
    }
    int nextId = 1;
    public Order save(Order order){
        if(order.getId() == 0){
            order.setId(nextId++);
        }
        orders.put(order.getId(), order);
        return order;
    }
    public void deleteAll(){
        orders.clear();
    }
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}
