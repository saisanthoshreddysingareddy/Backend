package com.example.ecom.repositories;

import com.example.ecom.models.Inventory;

import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository{
    Map<Integer, Inventory> inventories = new HashMap<>();
    // Interface methods
    public Optional<Inventory> findInventoryByProductId(int productId){
        if(inventories.containsKey(productId)){
            return Optional.of(inventories.get(productId));
        }
        return Optional.empty();
    }

    int nextId = 1;
    public Inventory save(Inventory inventory){
        if(inventory.getId() == 0){
            inventory.setId(nextId++);
        }
        inventories.put(inventory.getProduct().getId(), inventory);
        return inventory;
    }

    @Override
    public List<Inventory> findAllById(List<Integer> ids){
        List<Inventory> result = new ArrayList<>();

        for(Integer id : ids){
            if(inventories.containsKey(id)){
                Inventory original = inventories.get(id);

                Inventory copy = new Inventory();
                copy.setId(original.getId());
                copy.setProduct(original.getProduct());
                copy.setQuantity(original.getQuantity());

                result.add(copy);
            }
        }

        return result;
    }

    public void deleteAll(){
        inventories.clear();
    }
    public List<Inventory> findAll(){
        return new ArrayList<>(inventories.values());
    }
}
