package com.example.ecom.repositories;

import com.example.ecom.models.Address;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class AddressRepositoryImpl implements AddressRepository{
    Map<Integer, Address> addresses = new HashMap<>();

    // Interface methods
    public Optional<Address> findAddressById(int addressId){
        if(addresses.containsKey(addressId)){
            return Optional.of(addresses.get(addressId));
        }
        return Optional.empty();
    }

    int nextId = 1;
    public Address save(Address address){
        if(address.getId() == 0){
            address.setId(nextId++);
        }
        addresses.put(address.getId(), address);
        return address;
    }

    public void deleteAll(){
        addresses.clear();
    }

    public List<Address> findAll(){
        return new ArrayList<>(addresses.values());
    }
}
