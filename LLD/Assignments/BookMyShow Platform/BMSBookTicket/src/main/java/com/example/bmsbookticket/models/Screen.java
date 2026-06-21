package com.example.bmsbookticket.models;

import lombok.Data;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@Data
public class Screen extends BaseModel{
    private String name;
    @OneToMany
    private List<Seat> seats;

    private ScreenStatus status;
    private List<Feature> features;

    @ManyToOne
    private Theatre theatre;
}
