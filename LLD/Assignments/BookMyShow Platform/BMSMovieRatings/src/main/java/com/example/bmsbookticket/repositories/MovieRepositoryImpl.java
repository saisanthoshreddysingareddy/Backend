package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Movie;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository{
    Map<Integer, Movie> movies = new HashMap<>();

    // Interface methods
    public Optional<Movie> findMovieById(int movieId){
        if(movies.containsKey(movieId)){
            return Optional.of(movies.get(movieId));
        }
        return Optional.empty();
    }

    int nextId = 1;
    public Movie save(Movie movie){
        if(movie.getId() == 0){
            movie.setId(nextId++);
        }
        movies.put(movie.getId(), movie);
        return movie;
    }

    public void deleteAll(){
        movies.clear();
    }
}
