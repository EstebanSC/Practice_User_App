package com.esc.pro.users_app.Controllers;

import com.esc.pro.users_app.Services.UserService;
import com.esc.pro.users_app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserConroller {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                               @RequestParam(required = false, value = "size", defaultValue = "1000") int size) {
        return new ResponseEntity<>(userService.getUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }


    @GetMapping("/usernames")
    public ResponseEntity<Page<String>> getUsernames(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                   @RequestParam(required = false, value = "size", defaultValue = "1000") int size) {
        return new ResponseEntity<>(userService.getUserNames(page,size), HttpStatus.OK);
    }

    @GetMapping("/userName/{userName}")
    public ResponseEntity<User> getUserByName(@PathVariable("userName") String userName) {
        return new ResponseEntity<>(userService.getUserByUserName(userName), HttpStatus.OK);
    }
}
