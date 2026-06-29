package com.example.scaler.models;

import lombok.Data;
import jakarta.persistence.Entity;

@Entity
@Data
public class Batch extends BaseModel{

    private String name;
    private Schedule schedule;

}
