package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface UserRepository{
    public Optional<User> findUserById(int userId);
    public User save(User user);
    public void deleteAll();

}
