package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    Map<Integer, Inventory> inventories = new HashMap<>();

    // Interface methods
    public Inventory addInventory(Inventory inventory){
        inventories.put(inventory.getProduct().getId(), inventory);
        return inventory;
    }

    public Inventory deleteInventory(Inventory inventory){
        return inventories.remove(inventory.getProduct().getId());
    }

    public Optional<Inventory> findInventoryByProductId(int productId){
        return Optional.ofNullable(inventories.get(productId));
    }
    public void deleteAll() {
        inventories.clear();
    }

}
