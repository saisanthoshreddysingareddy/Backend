package com.example.ecom.repositories;

import java.util.Optional;

import com.example.ecom.models.User;

public interface UserRepository {
    public Optional<User> findByUserId(int userId);
    public User save(User user);
    void deleteAll();

}
