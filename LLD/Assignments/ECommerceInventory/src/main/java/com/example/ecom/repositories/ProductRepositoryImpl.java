package com.example.ecom.repositories;

import com.example.ecom.models.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    Map<Integer, Product> products = new HashMap<>();

    // Interface methods
    public Optional<Product> findByProductId(int productId){
        if(products.containsKey(productId)){
            return Optional.of(products.get(productId));
        }
        return Optional.empty();
    }
    private int nextId = 1;
    public Product save(Product product){
        if(product.getId() == 0){
            product.setId(nextId++);
        }
        products.put(product.getId(), product);
        return product;
    }
    public void deleteAll() {
        products.clear();
    }
}
