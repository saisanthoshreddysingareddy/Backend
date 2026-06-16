package com.example.ecom.repositories;

import com.example.ecom.models.OrderDetail;

import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailRepositoryImpl implements OrderDetailRepository{
    Map<Integer, OrderDetail> orderDetails = new HashMap<>();

    // Interface methods
    public Optional<OrderDetail> findOrderDetailsByOrderId(int orderId){
        if(orderDetails.containsKey(orderId)){
            return Optional.of(orderDetails.get(orderId));
        }
        return Optional.empty();
    }

    public OrderDetail save(OrderDetail orderDetail){
        orderDetails.put(orderDetail.getOrder().getId(), orderDetail);
        return orderDetail;
    }
    public void deleteAll(){
        orderDetails.clear();
    }
    public List<OrderDetail> findAll(){
        return new ArrayList<>(orderDetails.values());
    }
}
