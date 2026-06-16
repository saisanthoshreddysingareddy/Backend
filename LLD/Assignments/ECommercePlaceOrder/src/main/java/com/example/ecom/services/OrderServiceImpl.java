package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    UserRepository userRepository;
    ProductRepository productRepository;
    AddressRepository addressRepository;
    InventoryRepository inventoryRepository;
    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    HighDemandProductRepository highDemandProductRepository;

    public Order placeOrder(int userId, int addressId, List<Pair<Integer, Integer>> orderDetails) throws UserNotFoundException, InvalidAddressException, OutOfStockException, InvalidProductException, HighDemandProductException {
        // Check User existence
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();


        // Check User Selected Address Existence in DB
        Optional<Address> optionalAddress = addressRepository.findAddressById(addressId);
        if(optionalAddress.isEmpty()){
            throw new InvalidAddressException("Invalid Selected Address");
        }
        Address userSelectedAddress = optionalAddress.get(); // User Selected Address


        // Check Address belongs to user
        boolean isAddressValid = false;
        for(Address address : user.getAddresses()){
            if(address.getId() == userSelectedAddress.getId()){
                isAddressValid = true;
                break;
            }
        }
        if(isAddressValid == false){
            throw new InvalidAddressException("Address does not belongs to user");
        }


        // Check Products have enough quantity to fill the order
        for(Pair<Integer, Integer> productQuantity: orderDetails){
            int userSelectedProductId = productQuantity.getFirst();
            int userSelectedProductQuantity = productQuantity.getSecond();

            Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByProductId(userSelectedProductId);
            if(optionalInventory.isEmpty()){
                throw new InvalidProductException("Product does not exists");
            }
            Inventory inventory = optionalInventory.get();

            // Check stock
            if(inventory.getQuantity() < userSelectedProductQuantity){
                throw new OutOfStockException("Out of Stock");
            }

            // Check for high demand products
            Optional<HighDemandProduct> optionalHighDemandProduct = highDemandProductRepository.findHighDemandProductByProductId(productQuantity.getFirst());
            if(optionalHighDemandProduct.isPresent()){
                HighDemandProduct highDemandProduct = optionalHighDemandProduct.get();
                int maxAllowedQuantity = highDemandProduct.getMaxQuantity();
                if(productQuantity.getSecond() > maxAllowedQuantity){
                    throw new HighDemandProductException("Max allowed quantity is "+ highDemandProduct.getMaxQuantity());
                }
            }
        }

        List<OrderDetail> userSelectedOrderDetails = new ArrayList<>();
        for(Pair<Integer, Integer> productQuantity: orderDetails){
            // Build user selected order details list
            Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByProductId(productQuantity.getFirst());
            if(optionalInventory.isEmpty()){
                throw new InvalidProductException("Product does not exists");
            }
            Inventory inventory = optionalInventory.get();


            // Create Order Details list
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(inventory.getProduct());
            orderDetail.setQuantity(productQuantity.getSecond());

            userSelectedOrderDetails.add(orderDetail);

            // Update stock in inventory
            int updatedQuantity = inventory.getQuantity() - productQuantity.getSecond();
            inventory.setQuantity(updatedQuantity);

            inventoryRepository.save(inventory);
        }

        // Create Order
        Order order = new Order();
        order.setUser(user);
        order.setDeliveryAddress(userSelectedAddress);
        order.setOrderDetails(userSelectedOrderDetails);
        order.setOrderStatus(OrderStatus.PLACED);

        // Save Order
        Order savedOrder = orderRepository.save(order);

        for(OrderDetail orderDetail : userSelectedOrderDetails){
            orderDetail.setOrder(savedOrder);
            orderDetailRepository.save(orderDetail);
        }

        return savedOrder;
    }
}
