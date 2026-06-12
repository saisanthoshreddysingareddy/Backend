package com.example.ecom.services;

import com.example.ecom.adapters.NotificationAdapter;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Notification;
import com.example.ecom.models.NotificationStatus;
import com.example.ecom.models.Product;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService{

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private NotificationAdapter notificationAdapter;
    private NotificationRepository notificationRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                ProductRepository productRepository,
                                NotificationRepository notificationRepository,
                                NotificationAdapter notificationAdapter) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.notificationRepository = notificationRepository;
        this.notificationAdapter = notificationAdapter;
    }

    @Override
    public Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(product);
        Inventory inventory;
        if(inventoryOptional.isEmpty()){
            inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
        } else {
            inventory = inventoryOptional.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
        }

        Inventory savedInventory = inventoryRepository.save(inventory);
        notifyUsers(product);
        return savedInventory;
    }
    private void notifyUsers(Product product){
        // Get notifications
        Map<Integer, Notification> notifications = notificationRepository.getNotifications();
        for(Notification notification : notifications.values()){
            if(notification.getProduct().getId() == product.getId() &&
                    notification.getStatus() == NotificationStatus.PENDING){
                // Call email service notification
                notificationAdapter.sendNotification(notification.getUser().getEmail(), product.getName(), product.getDescription());
                notification.setStatus(NotificationStatus.SENT);
                notificationRepository.save(notification);
            }
        }
    }
}
