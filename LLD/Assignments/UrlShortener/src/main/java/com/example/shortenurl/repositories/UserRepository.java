package com.example.shortenurl.repositories;

import com.example.shortenurl.models.User;

import java.util.Optional;

public interface UserRepository{
    public Optional<User> findUserById(int userId);
    public User save(User user);
    public void deleteAll();
}