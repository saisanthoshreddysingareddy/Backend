package com.example.ecom.repositories;


import com.example.ecom.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository{
    public Optional<User> findUserById(int userId);
    public User save(User user);
    public void deleteAll();
    List<User> findAll();
}
