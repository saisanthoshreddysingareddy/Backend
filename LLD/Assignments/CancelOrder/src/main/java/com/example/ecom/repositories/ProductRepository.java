package com.example.ecom.repositories;

import com.example.ecom.models.Product;

import java.util.Optional;

public interface ProductRepository {
    public Optional<Product> findById(int productId);
    public Product save(Product product);
    public void deleteAll();
}