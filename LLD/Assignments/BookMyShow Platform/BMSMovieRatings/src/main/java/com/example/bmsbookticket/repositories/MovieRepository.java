package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Movie;

import java.util.Optional;

public interface MovieRepository {
    public Optional<Movie> findMovieById(int movieId);
    public Movie save(Movie movie);
    public void deleteAll();
}
