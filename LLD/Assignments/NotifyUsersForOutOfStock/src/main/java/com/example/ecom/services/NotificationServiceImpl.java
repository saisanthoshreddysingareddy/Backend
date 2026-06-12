package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    UserRepository userRepository;
    ProductRepository productRepository;
    InventoryRepository inventoryRepository;
    NotificationRepository notificationRepository;


    // Interface methods
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException{
        // Check user existence
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();

        // Check Product Existence
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product Not Found");
        }
        Product product = optionalProduct.get();

        // Check product out of stock
        Optional<Inventory> optionalInventory = inventoryRepository.findByProduct(product);
        if(optionalInventory.isEmpty()){
            throw new ProductNotFoundException("Product Not Found");
        }
        Inventory inventory = optionalInventory.get();
        if(inventory.getQuantity() > 0){
            throw new ProductInStockException("Product In Stock");
        }

        Notification notification = new Notification();
        notification.setProduct(product);
        notification.setUser(user);
        notification.setStatus(NotificationStatus.PENDING);

        Notification savedForNotification = notificationRepository.save(notification);
        return savedForNotification;
    }

    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException{
        // Check user existence
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();

        // Check Notification Existence
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if(optionalNotification.isEmpty()){
            throw new NotificationNotFoundException("Notification not found");
        }
        Notification notification = optionalNotification.get();
        if(notification.getUser().getId() != user.getId()){
            throw new UnAuthorizedException("UnAuthorized");
        }

        // Remove Notification from notifications
        notificationRepository.removeNotification(notification);


    }


}
