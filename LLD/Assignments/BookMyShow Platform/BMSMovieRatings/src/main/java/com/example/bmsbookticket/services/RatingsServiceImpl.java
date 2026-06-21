package com.example.bmsbookticket.services;

import com.example.bmsbookticket.exceptions.MovieNotFoundException;
import com.example.bmsbookticket.exceptions.RatingOutOfRangeException;
import com.example.bmsbookticket.exceptions.UserNotFoundException;
import com.example.bmsbookticket.models.Movie;
import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.repositories.MovieRepository;
import com.example.bmsbookticket.repositories.RatingRepository;
import com.example.bmsbookticket.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingsServiceImpl implements RatingsService{
    UserRepository userRepository;
    RatingRepository ratingRepository;
    MovieRepository movieRepository;

    // Interface methods
    public Rating rateMovie(int userId, int movieId, int rating) throws UserNotFoundException, MovieNotFoundException, RatingOutOfRangeException {
        // Check user existence
        Optional<User> optionalUser = userRepository.findUserById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();

        // Check movie existence
        Optional<Movie> optionalMovie = movieRepository.findMovieById(movieId);
        if(optionalMovie.isEmpty()){
            throw new MovieNotFoundException("Movie Not Found");
        }
        Movie movie = optionalMovie.get();

        // Check rating range
        if(rating<=0 || rating>5){
            throw new RatingOutOfRangeException("Rating Out of Range");
        }

        // Update - Check user has already rated the movie
        Optional<Rating> optionalExistingRatingObj = ratingRepository.findByUserAndMovie(user.getId(), movieId);
        if(optionalExistingRatingObj.isPresent()){
            Rating existingRating = optionalExistingRatingObj.get();
            existingRating.setRating(rating);
            return ratingRepository.save(existingRating);
        }

        // Save New Rating
        Rating ratingObj = new Rating();
        ratingObj.setUser(user);
        ratingObj.setMovie(movie);
        ratingObj.setRating(rating);

        return ratingRepository.save(ratingObj);
    }

    public double getAverageRating(int movieId) throws MovieNotFoundException{
        // Check movie existence
        Optional<Movie> optionalMovie = movieRepository.findMovieById(movieId);
        if(optionalMovie.isEmpty()){
            throw new MovieNotFoundException("Movie Not Found");
        }
        Movie movie = optionalMovie.get();

        // find average
        double userCount = 0;
        double totalRatings = 0;
        for(Rating rating : ratingRepository.findAll()){
            if(rating.getMovie().getId() == movie.getId()){
                userCount++;
                totalRatings += rating.getRating();
            }
        }
        if(userCount == 0 && totalRatings==0){
            return 0.0;
        }
        double average = totalRatings/userCount;

        return average;
    }
}
