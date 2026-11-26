package com.esc.applications.users.Services;

import com.esc.applications.users.Repositories.AddressRepository;
import com.esc.applications.users.Repositories.ProfileRepository;
import com.esc.applications.users.entities.Address;
import com.esc.applications.users.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Address> findAdressesByProfileIdAndUserId(Integer userId, Integer profileId){
        return addressRepository.findByUserIdAndProfileId(userId, profileId);
    }

    public Address create (Integer userId, Integer profileId, Address address) {
        Optional<Profile> profile = profileRepository.getByUserIdAndByProfileId(userId,profileId);
        if(profile.isPresent()) {
            address.setProfile(profile.get());
            return addressRepository.save(address);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("There is not any Profile with Id: %d and UserId: %d", profileId,userId));
        }
    }
}
