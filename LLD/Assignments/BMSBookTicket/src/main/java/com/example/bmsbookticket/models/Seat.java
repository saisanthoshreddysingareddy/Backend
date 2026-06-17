package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Seat extends BaseModel{
    private String name;
    @Enumerated(EnumType.STRING)
    SeatType seatType;
    @ManyToOne
    Screen screen;
}
