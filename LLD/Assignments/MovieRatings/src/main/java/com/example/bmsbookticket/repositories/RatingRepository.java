package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Rating;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RatingRepository{
    public Optional<Rating> findByUserAndMovie(int userId, int movieId);
    public Rating save(Rating rating);
    public void deleteAll();
    // public Map<String,Rating> getRatings();
    public List<Rating> findAll();
}
