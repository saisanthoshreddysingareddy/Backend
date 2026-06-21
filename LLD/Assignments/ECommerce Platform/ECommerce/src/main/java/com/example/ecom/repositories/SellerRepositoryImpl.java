package com.example.ecom.repositories;

import com.example.ecom.models.Seller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class SellerRepositoryImpl implements SellerRepository{
    Map<Integer, Seller> sellers = new HashMap<>();

    // Interface methods
    public Optional<Seller> findSellerById(int sellerId){
        if(sellers.containsKey(sellerId)){
            return Optional.of(sellers.get(sellerId));
        }
        return Optional.empty();
    }
    public Seller save(Seller seller){
        sellers.put(seller.getId(), seller);
        return seller;
    }
    public void deleteAll(){
        sellers.clear();
    }
}
