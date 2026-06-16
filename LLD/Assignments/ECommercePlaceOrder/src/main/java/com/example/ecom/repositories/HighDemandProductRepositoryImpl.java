package com.example.ecom.repositories;

import com.example.ecom.models.HighDemandProduct;

import java.util.*;
import org.springframework.stereotype.Repository;

@Repository
public class HighDemandProductRepositoryImpl implements HighDemandProductRepository{
    Map<Integer, HighDemandProduct> highDemandProducts = new HashMap<>();

    // Interface methods
    public Optional<HighDemandProduct> findHighDemandProductByProductId(int productId){
        if(highDemandProducts.containsKey(productId)){
            return Optional.of(highDemandProducts.get(productId));
        }
        return Optional.empty();
    }
    public HighDemandProduct save(HighDemandProduct highDemandProduct){
        highDemandProducts.put(highDemandProduct.getProduct().getId(), highDemandProduct);
        return highDemandProduct;
    }
    public void deleteAll(){
        highDemandProducts.clear();
    }
    public List<HighDemandProduct> findAll(){
        return new ArrayList<>(highDemandProducts.values());
    }
}
