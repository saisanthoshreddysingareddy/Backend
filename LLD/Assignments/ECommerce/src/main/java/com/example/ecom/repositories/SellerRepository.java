package com.example.ecom.repositories;

import com.example.ecom.models.Seller;

import java.util.Optional;

public interface SellerRepository  {
    public Optional<Seller> findSellerById(int sellerId);
    public Seller save(Seller seller);
    public void deleteAll();
}
