package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.exceptions.UnAuthorizedAccessException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.models.UserType;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService{
    UserRepository userRepository;
    ProductRepository productRepository;
    InventoryRepository inventoryRepository;

    public Inventory createOrUpdateInventory(int userId, int productId, int quantity) throws ProductNotFoundException, UserNotFoundException, UnAuthorizedAccessException{
        // Get User
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        // Admin check
        if(!user.getUserType().equals(UserType.ADMIN)){
            throw new UnAuthorizedAccessException(
                    "Only admins can manage inventory");
        }

        // Get Product
        Optional<Product> optionalProduct = productRepository.findByProductId(productId);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }
        Product product = optionalProduct.get();

        // Check existing Inventory for quantity
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByProductId(product.getId());
        if(optionalInventory.isEmpty()){
            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
            return inventoryRepository.addInventory(inventory);
        }

        Inventory inventory = optionalInventory.get();
        inventory.setQuantity(inventory.getQuantity() + quantity);
        Inventory addedInventory = inventoryRepository.addInventory(inventory);
        return addedInventory;
    }

    public void deleteInventory(int userId, int productId) throws  UserNotFoundException, UnAuthorizedAccessException{
        // Get User
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        // Admin check
        if(!user.getUserType().equals(UserType.ADMIN)){
            throw new UnAuthorizedAccessException(
                    "Only admins can manage inventory");
        }

        // Get Product
        Optional<Product> optionalProduct = productRepository.findByProductId(productId);
        if(optionalProduct.isEmpty()){
            throw new UnAuthorizedAccessException("Unauthorized Access");
        }
        // Product product = optionalProduct.get();

        // Get Inventory for previous quantity
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByProductId(productId);
        if(optionalInventory.isPresent()){
            inventoryRepository.deleteInventory(optionalInventory.get());
        }

    }

}
