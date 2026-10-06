package com.esc.pro.users_app.Controllers;

import com.esc.pro.users_app.Services.AddressService;
import com.esc.pro.users_app.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/profiles/{profileId}/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> findAddressesByProfileAndUserId(@PathVariable("userId") Integer userId, @PathVariable("profileId") Integer profileId){
        return new ResponseEntity<>(addressService.findAdressesByProfileIdAndUserId(userId, profileId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Address> create(@PathVariable("userId") Integer userId, @PathVariable("profileId") Integer profileId, @RequestBody Address address) {
        return new ResponseEntity<>(addressService.create(userId, profileId, address),HttpStatus.CREATED);
    }
}
