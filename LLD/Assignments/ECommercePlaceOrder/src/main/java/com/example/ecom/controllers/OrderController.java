package com.example.ecom.controllers;

import com.example.ecom.dtos.PlaceOrderRequestDto;
import com.example.ecom.dtos.PlaceOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.Order;
import com.example.ecom.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    OrderService orderService;
    // Constructor
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    public PlaceOrderResponseDto placeOrder(PlaceOrderRequestDto placeOrderRequestDto) {
        PlaceOrderResponseDto responseDto = new PlaceOrderResponseDto();

        try{
            Order order = orderService.placeOrder(placeOrderRequestDto.getUserId(), placeOrderRequestDto.getAddressId(),placeOrderRequestDto.getOrderDetails());
            responseDto.setOrder(order);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        }catch (Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }

}
