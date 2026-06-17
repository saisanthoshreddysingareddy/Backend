package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.*;
import com.example.bmsbookticket.models.*;
import com.example.bmsbookticket.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShowServiceImpl implements ShowService {
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final SeatTypeShowRepository seatTypeShowRepository;
    private final ShowSeatRepository showSeatRepository;

    @Override
    @Transactional
    public Show createShow(int userId, int movieId, int screenId, Date startTime, Date endTime, 
                           List<Pair<SeatType, Double>> pricingConfig, List<Feature> features) 
                           throws MovieNotFoundException, ScreenNotFoundException, FeatureNotSupportedByScreen, 
                                  InvalidDateException, UserNotFoundException, UnAuthorizedAccessException {
        
        // Check User Existence & Authorization
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();
        if (user.getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("Unauthorized Access");
        }

        // Check Movie Existence
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException("Movie Not Found");
        }
        Movie movie = optionalMovie.get();

        // Check Screen Existence
        Optional<Screen> optionalScreen = screenRepository.findById(screenId);
        if (optionalScreen.isEmpty()) {
            throw new ScreenNotFoundException("Screen Not Found");
        }
        Screen screen = optionalScreen.get();

        // Robust Feature Validation
        List<Feature> screenSupportedFeatures = screen.getFeatures();
        if (features != null && !features.isEmpty()) {
            if (screenSupportedFeatures == null) {
                throw new FeatureNotSupportedByScreen("Feature not supported by screen");
            }

            for (Feature feature : features) {
                if (!screenSupportedFeatures.contains(feature)) {
                    throw new FeatureNotSupportedByScreen("Feature not supported by screen");
                }
            }
        }

        //  Basic Data Validation Checks
        if (startTime == null || endTime == null) {
            throw new InvalidDateException("Dates cannot be null");
        }
        if (startTime.before(new Date())) {
            throw new InvalidDateException("Show cannot start in past");
        }
        if (!startTime.before(endTime)) {
            throw new InvalidDateException("Show ends before it starts");
        }

        //  Create and Save Parent Show Object
        Show show = new Show();
        show.setMovie(movie);
        show.setFeatures(features != null ? features : new ArrayList<>());
        show.setScreen(screen);
        show.setStartTime(startTime);
        show.setEndTime(endTime);
        Show savedShow = showRepository.save(show);

        //  Store Pricing Details
        if (pricingConfig != null) {
            List<SeatTypeShow> seatTypeShows = new ArrayList<>();
            for (Pair<SeatType, Double> seatTypePrice : pricingConfig) {
                SeatTypeShow seatTypeShow = new SeatTypeShow();
                seatTypeShow.setShow(savedShow);
                seatTypeShow.setSeatType(seatTypePrice.getFirst());
                seatTypeShow.setPrice(seatTypePrice.getSecond());
                seatTypeShows.add(seatTypeShow);
            }
            seatTypeShowRepository.saveAll(seatTypeShows);
        }

        // Store Show Seats Mapping
        if (screen.getSeats() != null) {
            List<ShowSeat> showSeatList = new ArrayList<>();
            for (Seat seat : screen.getSeats()) {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setShow(savedShow);
                showSeat.setSeat(seat);
                showSeat.setStatus(SeatStatus.AVAILABLE);
                showSeatList.add(showSeat);
            }
            showSeatRepository.saveAll(showSeatList);
        }

        // Fetch and return clean transaction state
        return showRepository.findById(savedShow.getId()).orElse(savedShow);
    }
}