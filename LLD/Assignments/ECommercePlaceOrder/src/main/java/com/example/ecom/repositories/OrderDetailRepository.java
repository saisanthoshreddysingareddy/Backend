package com.example.ecom.repositories;


import com.example.ecom.models.OrderDetail;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepository {
    public Optional<OrderDetail> findOrderDetailsByOrderId(int orderId);
    public OrderDetail save(OrderDetail orderDetail);
    public void deleteAll();
    public List<OrderDetail> findAll();
}
