package com.example.scaler.models;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Batch extends BaseModel {

    private String name;

    @Enumerated(EnumType.STRING)
    private Schedule schedule;
}