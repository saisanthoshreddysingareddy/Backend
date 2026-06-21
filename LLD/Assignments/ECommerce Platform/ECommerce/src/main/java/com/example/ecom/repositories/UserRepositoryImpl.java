package com.example.ecom.repositories;

import com.example.ecom.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

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
    public User save(User user){
        users.put(user.getId(), user);
        return user;
    }
    public void deleteAll(){
        users.clear();
    }
}
