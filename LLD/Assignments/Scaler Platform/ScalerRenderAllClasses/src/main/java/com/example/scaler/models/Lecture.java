package com.example.scaler.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Lecture extends BaseModel {

    private String name;

    private String description;
}
