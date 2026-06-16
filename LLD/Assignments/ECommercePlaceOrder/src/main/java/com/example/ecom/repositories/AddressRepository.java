package com.example.ecom.repositories;


import com.example.ecom.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository{
    public Optional<Address> findAddressById(int addressId);
    public Address save(Address address);
    public void deleteAll();
    public List<Address> findAll();
}
