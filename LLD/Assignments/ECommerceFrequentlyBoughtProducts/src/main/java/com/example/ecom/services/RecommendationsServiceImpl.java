package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.Product;
import com.example.ecom.models.ProductGroup;
import com.example.ecom.repositories.ProductGroupsRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecommendationsServiceImpl implements RecommendationsService{
    ProductRepository productRepository;
    ProductGroupsRepository productGroupsRepository;

    // Constructor
    public RecommendationsServiceImpl(ProductRepository productRepository,
                                      ProductGroupsRepository productGroupsRepository){
        this.productRepository = productRepository;
        this.productGroupsRepository = productGroupsRepository;
    }

    public List<Product> getRecommendations(int productId) throws ProductNotFoundException{
        // Check Product Existence
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }
        Product product = optionalProduct.get();

        // Get all product groups
        List<ProductGroup> productGroupList = productGroupsRepository.findByProductsContaining(product);
        List<Product> productsToSuggest = new ArrayList<>();
        if(!productGroupList.isEmpty()){
            for(ProductGroup productGroup : productGroupList){
                if(productGroup.getProducts().contains(product)){
                    for(Product eachProduct : productGroup.getProducts()){
                        if(eachProduct.getId() != product.getId() && !productsToSuggest.contains(eachProduct)){
                            productsToSuggest.add(eachProduct);
                        }
                    }
                }
            }
        }

        return productsToSuggest ;
    }


}
