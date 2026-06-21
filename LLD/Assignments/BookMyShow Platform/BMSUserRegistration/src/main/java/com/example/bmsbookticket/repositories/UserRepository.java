package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository{
    public Optional<User> findUserByEmail(String email);
    public User save(User user);
    public void deleteAll();
    List<User> findAll(); 

}