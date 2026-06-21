package com.example.ecom.services;

import com.example.ecom.exceptions.OrderCannotBeCancelledException;
import com.example.ecom.exceptions.OrderDoesNotBelongToUserException;
import com.example.ecom.exceptions.OrderNotFoundException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.OrderRepository;
import com.example.ecom.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    UserRepository userRepository;
    OrderRepository orderRepository;
    InventoryRepository inventoryRepository;

    // Interface method
    public Order cancelOrder(int orderId, int userId)  throws UserNotFoundException, OrderNotFoundException, OrderDoesNotBelongToUserException, OrderCannotBeCancelledException{
        // Check User Existence
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();

        // Check Order Existence
        Optional<Order> optionalOrder = orderRepository.findOrderById(orderId);
        if(optionalOrder.isEmpty()){
            throw new OrderNotFoundException("Order Not Found");
        }
        Order order = optionalOrder.get();

        // Check Order belongs to user or not
        if(order.getUser().getId() != user.getId()){
            throw new OrderDoesNotBelongToUserException("Order Does Not Belong To User");
        }

        // Check Order Status - cannot be cancelled if SHIPPED or DELIVERED or CANCELLED
        if(order.getOrderStatus() != OrderStatus.PLACED){
            throw new OrderCannotBeCancelledException("Order Cannot Be Cancelled");
        }

        // Adding quantity back to inventory
        for(OrderDetail orderDetail : order.getOrderDetails()){
            int productQuantity = orderDetail.getQuantity();
            Optional<Inventory> optionalInventory = inventoryRepository.findByProduct(orderDetail.getProduct());
            if(optionalInventory.isPresent()){
                Inventory inventory = optionalInventory.get();
//                inventory.setProduct(orderDetail.getProduct());
                inventory.setQuantity(inventory.getQuantity() + productQuantity);
                inventoryRepository.save(inventory);
            }
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        Order finalOrder = orderRepository.save(order);

        return finalOrder;
    }

}
