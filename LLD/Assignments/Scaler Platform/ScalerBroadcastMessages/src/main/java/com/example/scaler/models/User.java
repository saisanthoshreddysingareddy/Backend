package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User extends BaseModel{

    private String name;
    private String email;
    private UserType userType;
}
