package com.example.splitwise.models;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group extends BaseModel {

    private String name;
    private String description;
    private Date createdAt;
}