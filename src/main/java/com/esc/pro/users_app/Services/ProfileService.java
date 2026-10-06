package com.esc.pro.users_app.Services;

import com.esc.pro.users_app.Repositories.ProfileRepository;
import com.esc.pro.users_app.Repositories.UserRepository;
import com.esc.pro.users_app.entities.Profile;
import com.esc.pro.users_app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    public Profile create(Integer userId, Profile profile) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            profile.setUser(user.get()) ;
            return  profileRepository.save(profile);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found",userId));
        }

    }

    public Profile getProfileByUserIdAndProfileId(Integer userId, Integer profileId) {
        return profileRepository.getByUserIdAndByProfileId(userId, profileId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,String.format("Profile not found with userId: %d, and ProfileId: %d",userId,profileId)));
    }

}
