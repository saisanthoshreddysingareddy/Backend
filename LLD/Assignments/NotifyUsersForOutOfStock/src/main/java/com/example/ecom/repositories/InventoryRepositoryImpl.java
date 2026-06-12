package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository{
    Map<Integer, Inventory> inventories = new HashMap<>();

    // Interface methods
    public Optional<Inventory> findByProduct(Product product){
        if(inventories.containsKey(product.getId())){
            return Optional.of(inventories.get(product.getId()));
        }
        return Optional.empty();
    }
    private int nextId = 1;
    public Inventory save(Inventory inventory){
        if(inventory.getId() == 0){
            inventory.setId(nextId++);
        }
        inventories.put(inventory.getProduct().getId(), inventory);
        return inventory;
    }
    public void deleteAll(){
        inventories.clear();
    }
}
