package com.example.ecom.repositories;

import com.example.ecom.models.Notification;
import com.example.ecom.models.Product;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository{
    @Getter
    Map<Integer, Notification> notifications = new HashMap<>();

    // Interface methods
    public Optional<Notification> findById(int notificationId){
        if(notifications.containsKey(notificationId)){
            return Optional.of(notifications.get(notificationId));
        }
        return Optional.empty();
    }
    private int nextId = 1;
    public Notification save(Notification notification){
        if(notification.getId() == 0){
            notification.setId(nextId++);
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }
    public void removeNotification(Notification notification){
        if(notifications.containsKey(notification.getId())){
            notifications.remove(notification.getId());
        }
    }

    public void deleteAll(){
        notifications.clear();
    }
    public long count() {
        return notifications.size();
    }
    
    public List<Notification> findAll() {
        return new ArrayList<>(notifications.values());
    }
    
    public List<Notification> findByProduct(Product product) {
        List<Notification> result = new ArrayList<>();
    
        for(Notification notification : notifications.values()) {
            if(notification.getProduct().getId() == product.getId()) {
                result.add(notification);
            }
        }
    
        return result;
    }
}
