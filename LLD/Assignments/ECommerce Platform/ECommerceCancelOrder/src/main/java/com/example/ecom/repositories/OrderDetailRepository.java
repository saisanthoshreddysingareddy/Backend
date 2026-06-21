package com.example.ecom.repositories;

import com.example.ecom.models.Order;
import com.example.ecom.models.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository {
    public Optional<OrderDetail> findOrderDetailById(int orderDetailId);
    public OrderDetail save(OrderDetail orderDetail);
    public void deleteAll();
    List<OrderDetail> saveAll(List<OrderDetail> orderDetails);
}
