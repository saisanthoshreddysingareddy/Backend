package com.example.splitwise.repositories;

import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}