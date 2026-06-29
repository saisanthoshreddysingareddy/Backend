package com.example.scaler.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Entity
@Data
public class Batch extends BaseModel{

    private String name;
    @Enumerated
    private Schedule schedule;

}
