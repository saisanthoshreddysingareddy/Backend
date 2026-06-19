package com.example.ecom.services;

import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Advertisement;
import com.example.ecom.models.Preference;
import com.example.ecom.models.User;
import com.example.ecom.repositories.AdvertisementRepository;
import com.example.ecom.repositories.PreferencesRepository;
import com.example.ecom.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdsServiceImpl implements AdsService{
    UserRepository userRepository;
    AdvertisementRepository advertisementRepository;
    PreferencesRepository preferencesRepository;

    // Interface Method
    @Transactional
    public Advertisement getAdvertisementForUser(int userId) throws UserNotFoundException, Exception{
        // Check User Existence
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        User user = optionalUser.get();
        System.out.println(user.getPreferences());

        // Assign advertisement if preferences are not empty
        if(user.getPreferences().size() != 0){
            for(Preference preference : user.getPreferences()){
                Optional<Advertisement> optionalAdvertisement = advertisementRepository.findByPreferenceId(preference.getId());
                if(optionalAdvertisement.isPresent()){
                    return optionalAdvertisement.get();
                }
            }
        }

        // Get all advertisements if preferences are empty
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        if(advertisementList.isEmpty()){
            throw new Exception("No advertisements in the database");
        }
        return advertisementList.get(0);
    }
}
