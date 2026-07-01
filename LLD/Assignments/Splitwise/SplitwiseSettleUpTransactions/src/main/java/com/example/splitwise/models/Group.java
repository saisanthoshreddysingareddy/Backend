package com.example.splitwise.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Group extends BaseModel {

    private String name;

    private String description;

    @ManyToMany
    private List<User> users;

    @ManyToMany
    private List<User> admins;
}