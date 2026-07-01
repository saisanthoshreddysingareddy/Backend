package com.example.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class Group extends BaseModel {

    private String name;

    private String description;
}