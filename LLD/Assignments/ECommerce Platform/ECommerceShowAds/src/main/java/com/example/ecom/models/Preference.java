package com.example.ecom.models;

import lombok.Data;

import java.util.Date;

import jakarta.persistence.Entity;

@Entity
@Data
public class Preference extends BaseModel{
    private String category;
    private String description;
    private Date createdAt;
}
