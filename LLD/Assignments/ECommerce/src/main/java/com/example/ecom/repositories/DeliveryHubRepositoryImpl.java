package com.example.ecom.repositories;

import com.example.ecom.models.DeliveryHub;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class DeliveryHubRepositoryImpl implements DeliveryHubRepository{
    Map<String, DeliveryHub> deliveryHubs = new HashMap<>();

    // Interface methods
    public Optional<DeliveryHub> findDeliveryHubByZipCode(String  zipCode){
        if(deliveryHubs.containsKey(zipCode)){
            return Optional.of(deliveryHubs.get(zipCode));
        }
        return Optional.empty();
    }
    public DeliveryHub save(DeliveryHub deliveryHub){
        deliveryHubs.put(deliveryHub.getAddress().getZipCode(), deliveryHub);
        return deliveryHub;
    }
    public void deleteAll(){
        deliveryHubs.clear();
    }

}
