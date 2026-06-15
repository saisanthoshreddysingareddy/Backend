package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository{
    Map<Integer, User> users = new HashMap<>();

    // Interface methods
    public Optional<User> findUserById(int userId){
        if(users.containsKey(userId)){
            return Optional.of(users.get(userId));
        }
        return Optional.empty();
    }

    int nextId = 1;
    public User save(User user){
        if(user.getId() == 0){
            user.setId(nextId++);
        }
        users.put(user.getId(), user);
        return user;
    }

    public void deleteAll(){
        users.clear();
    }
}
