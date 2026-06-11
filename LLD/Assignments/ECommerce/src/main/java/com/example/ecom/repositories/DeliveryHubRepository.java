package com.example.ecom.repositories;

import com.example.ecom.models.DeliveryHub;

import java.util.Optional;

public interface DeliveryHubRepository {
    public Optional<DeliveryHub> findDeliveryHubByZipCode(String zipCode);
    public DeliveryHub save(DeliveryHub deliveryHub);
    public void deleteAll();
}
