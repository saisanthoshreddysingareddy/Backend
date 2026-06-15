package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // Interface methods
    public User signupUser(String name, String email, String password) throws Exception{
        // Check User Exists or not before Register
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            throw new Exception("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public boolean login(String email, String password) throws Exception{
        boolean isLoggedIn = false;
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isEmpty()){
            throw new Exception("User does not exists");
        }
        User user = optionalUser.get();
        if(passwordEncoder.matches(password, user.getPassword())){
            isLoggedIn = true;
        }
        return isLoggedIn;
    }
}
