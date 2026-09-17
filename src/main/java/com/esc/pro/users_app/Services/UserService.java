package com.esc.pro.users_app.Services;

import com.esc.pro.users_app.Repositories.UserRepository;
import com.esc.pro.users_app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Page<User> getUsers(int page, int size) {
        return  userRepository.findAll(PageRequest.of(page, size));
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId)));
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with username : %s not found", userName)));
    }

    public Page<String> getUserNames(int page, int size) {
        return userRepository.findUserNames(PageRequest.of(page,size));
    }

}
