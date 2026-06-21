package com.example.ecom.controllers;

import org.springframework.stereotype.Controller;

import com.example.ecom.dtos.CancelOrderRequestDto;
import com.example.ecom.dtos.CancelOrderResponseDto;
import com.example.ecom.dtos.ResponseStatus;
import com.example.ecom.models.Order;
import com.example.ecom.services.OrderService;

@Controller
public class OrderController {
    OrderService orderService;
    
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    public CancelOrderResponseDto cancelOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        CancelOrderResponseDto responseDto = new CancelOrderResponseDto();
        try{
            Order order= orderService.cancelOrder(cancelOrderRequestDto.getOrderId(), cancelOrderRequestDto.getUserId());
            responseDto.setOrder(order);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        }
        catch(Exception e){
            responseDto.setStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }

}
