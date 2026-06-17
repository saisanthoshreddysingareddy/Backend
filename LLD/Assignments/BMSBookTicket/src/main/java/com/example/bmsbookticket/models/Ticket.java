package com.example.bmsbookticket.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@Data
public class Ticket extends BaseModel{
    @ManyToOne
    private Show show;

    @ManyToMany
    List<Seat> seats;
    private Date timeOfBooking;

    @ManyToOne
    private User user;

    private TicketStatus status;
}
