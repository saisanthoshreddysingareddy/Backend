package com.example.bmsbookticket.services;

import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService{
    UserRepository userRepository;
    SeatsRepository seatsRepository;
    ShowSeatRepository showSeatRepository;
    ShowRepository showRepository;
    TicketRepository ticketRepository;

    public TicketServiceImpl(
            UserRepository userRepository,
            SeatsRepository seatsRepository,
            ShowSeatRepository showSeatRepository,
            ShowRepository showRepository,
            TicketRepository ticketRepository) {

        this.userRepository = userRepository;
        this.seatsRepository = seatsRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.ticketRepository = ticketRepository;
    }

    // Interface method
    public Ticket bookTicket(List<Integer> showSeatIds, int userId) throws Exception{
        // Validate User
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        User user = optionalUser.get();

        // Validate seat list
        if(showSeatIds == null || showSeatIds.isEmpty()) {
            throw new Exception("No seats selected");
        }

        // Fetch all ShowSeats
        List<ShowSeat> showSeats = new ArrayList<>();
        for(Integer showSeatId : showSeatIds) {
            Optional<ShowSeat> optionalShowSeat = showSeatRepository.findById(showSeatId);
            if(optionalShowSeat.isEmpty()) {
                throw new Exception("ShowSeat not found : " + showSeatId);
            }
            showSeats.add(optionalShowSeat.get());
        }

        // Get show from first seat
        Show show = showSeats.get(0).getShow();

        // Check all seats belong to same show
        for(ShowSeat showSeat : showSeats) {
            if(showSeat.getShow().getId() != show.getId()) {
                throw new Exception("Seats belong to different shows");
            }
        }

        // Check availability
        List<Integer> unavailableSeatIds = new ArrayList<>();
        for(ShowSeat showSeat : showSeats) {
            if(showSeat.getStatus() != SeatStatus.AVAILABLE) {
                unavailableSeatIds.add(showSeat.getId());
            }
        }

        if(!unavailableSeatIds.isEmpty()) {
            throw new Exception("Seats are not available : " + unavailableSeatIds);
        }

        // Block seats
        List<Seat> bookedSeats = new ArrayList<>();
        for(ShowSeat showSeat : showSeats) {
            showSeat.setStatus(SeatStatus.BLOCKED);
            showSeatRepository.save(showSeat);
            bookedSeats.add(showSeat.getSeat());
        }

        // Create ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setSeats(bookedSeats);
        ticket.setStatus(TicketStatus.UNPAID);
        ticket.setTimeOfBooking(new Date());

        ticketRepository.save(ticket);

        return ticket;
    }

}
