package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{
    Map<String, User> users = new HashMap<>();

    // Interface methods
    public Optional<User> findUserByEmail(String email){
        if(users.containsKey(email)){
            return Optional.of(users.get(email));
        }
        return Optional.empty();
    }

    int nextId = 1;
    public User save(User user){
        if(user.getId() == 0){
            user.setId(nextId++);
        }
        users.put(user.getEmail(), user);
        return user;
    }
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public void deleteAll(){
        users.clear();
    }
}