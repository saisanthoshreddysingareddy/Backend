package com.example.qcommerce.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Partner extends BaseModel{
    private String name;
    @Embedded
    private Location currentLocation;
}
