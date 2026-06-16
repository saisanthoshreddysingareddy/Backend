package com.example.ecom.repositories;


import com.example.ecom.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    public Optional<Product> findProductById(int productId);
    public Product save(Product product);
    public void deleteAll();
    public List<Product> findAll();
}

