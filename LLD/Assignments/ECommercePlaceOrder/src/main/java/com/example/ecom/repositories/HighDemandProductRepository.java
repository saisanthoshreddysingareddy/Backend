package com.example.ecom.repositories;


import com.example.ecom.models.HighDemandProduct;

import java.util.List;
import java.util.Optional;

public interface HighDemandProductRepository {
    public Optional<HighDemandProduct> findHighDemandProductByProductId(int productId);
    public HighDemandProduct save(HighDemandProduct highDemandProduct);
    public void deleteAll();
    public List<HighDemandProduct> findAll();
}
