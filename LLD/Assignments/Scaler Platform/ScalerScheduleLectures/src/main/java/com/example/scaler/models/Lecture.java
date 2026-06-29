package com.example.scaler.models;

import lombok.Data;
import jakarta.persistence.Entity;

@Entity
@Data
public class Lecture extends BaseModel{

    private String name;
    private String description;
}
