package com.example.bmsbookticket.repositories;

import com.example.bmsbookticket.models.Rating;
import com.example.bmsbookticket.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Getter
@Setter
public class RatingRepositoryImpl implements RatingRepository{
    Map<String, Rating> ratings = new HashMap<>();

    // Interface methods
    public Optional<Rating> findByUserAndMovie(int userId, int movieId){
        String userMovieCombo = userId+"_"+movieId;
        if(ratings.containsKey(userMovieCombo)){
            return Optional.of(ratings.get(userMovieCombo));
        }
        return Optional.empty();
    }

    int nextId = 1;
    public Rating save(Rating rating){
        if(rating.getId() == 0){
            rating.setId(nextId++);
        }
        String userMovieString = rating.getUser().getId()+"_"+rating.getMovie().getId();
        ratings.put(userMovieString, rating);
        return rating;
    }
    
    @Override
    public List<Rating> findAll() {
        return new ArrayList<>(ratings.values());
    }

    public void deleteAll(){
        ratings.clear();
    }
}
