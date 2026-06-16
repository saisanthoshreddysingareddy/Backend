package com.example.ecom.repositories;


import com.example.ecom.models.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository{
    public Optional<Inventory> findInventoryByProductId(int productId);
    public Inventory save(Inventory inventory);
    public void deleteAll();
    public List<Inventory> findAll();
    List<Inventory> findAllById(List<Integer> ids);

}
