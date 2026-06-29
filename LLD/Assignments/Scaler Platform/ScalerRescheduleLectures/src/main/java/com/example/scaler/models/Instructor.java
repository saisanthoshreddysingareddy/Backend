package com.example.scaler.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Instructor extends BaseModel{

    private String name;
    private String email;
}
