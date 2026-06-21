package com.example.ecom.repositories;

import com.example.ecom.models.Notification;
import com.example.ecom.models.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NotificationRepository {
    public Optional<Notification> findById(int notificationId);
    public Notification save(Notification notification);
    public void deleteAll();
    public void removeNotification(Notification notification);
    public Map<Integer, Notification> getNotifications();
    public long count();
    public List<Notification> findAll();
    public List<Notification> findByProduct(Product product);
}
