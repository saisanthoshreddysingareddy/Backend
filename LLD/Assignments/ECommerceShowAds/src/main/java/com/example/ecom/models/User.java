package com.example.ecom.models;

import lombok.Data;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity(name = "users")
@Data
public class User extends BaseModel{
    private String name;
    private String email;
    @OneToMany
    private List<Preference> preferences;
}
