package com.example.ecom.repositories;

import com.example.ecom.models.Order;
import com.example.ecom.models.OrderDetail;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderDetailRepositoryImpl implements OrderDetailRepository {
    Map<Integer, OrderDetail> orderDetails = new HashMap<>();

    // Interface methods
    public Optional<OrderDetail> findOrderDetailById(int orderDetailId){
        if(orderDetails.containsKey(orderDetailId)){
            return Optional.of(orderDetails.get(orderDetailId));
        }
        return Optional.empty();
    }
    int nextId = 1;
    public OrderDetail save(OrderDetail orderDetail){
        if(orderDetail.getId() ==0 ){
            orderDetail.setId(nextId++);
        }
        orderDetails.put(orderDetail.getId(), orderDetail);
        return orderDetail;
    }
    public void deleteAll(){
        orderDetails.clear();
    }
    public List<OrderDetail> saveAll(List<OrderDetail> orderDetails) {
        for (OrderDetail od : orderDetails) {
            save(od);
        }
        return orderDetails;
    }
}
