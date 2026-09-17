package com.esc.pro.users_app.Controllers;

import com.esc.pro.users_app.Models.User;
import com.esc.pro.users_app.Services.UserServiceByList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("RestCV1")
public class PracticeController {
    @Autowired
    private UserServiceByList userService;

    @GetMapping("greeting")
    public ResponseEntity<String> getGreeting(){
        return new ResponseEntity<>(new String("Hello World"),HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers(@RequestParam(value="startsWith", required = false) String startsWith){
        return new ResponseEntity<>(userService.getUsers(startsWith),HttpStatus.OK);
    }
    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUserByName(@PathVariable("username") String userName){
         return new ResponseEntity<>(userService.getUserByName(userName),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createuser(@RequestBody  User user){
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String userName){
        userService.deleteUser(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
