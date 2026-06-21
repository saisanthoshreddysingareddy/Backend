package com.example.ecom.repositories;

import java.util.Optional;

import com.example.ecom.models.Inventory;

public interface InventoryRepository {
    public Inventory addInventory(Inventory inventory);
    public Inventory deleteInventory(Inventory inventory);
    public Optional<Inventory> findInventoryByProductId(int productId);
    void deleteAll();

}
