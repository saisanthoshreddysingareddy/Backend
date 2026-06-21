package com.example.bmsbookticket.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class SeatTypeShow extends BaseModel{
    @ManyToOne
    private Show show;

    private SeatType seatType;
    private double price;
}
