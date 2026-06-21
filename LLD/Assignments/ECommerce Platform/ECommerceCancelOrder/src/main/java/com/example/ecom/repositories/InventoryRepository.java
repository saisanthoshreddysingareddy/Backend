package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    public Optional<Inventory> findByProduct(Product product);
    public Inventory save(Inventory inventory);
    public void deleteAll();
    public List<Inventory> findAll();
}