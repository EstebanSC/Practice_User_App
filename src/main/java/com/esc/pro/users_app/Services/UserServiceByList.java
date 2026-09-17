package com.esc.pro.users_app.Services;

import com.esc.pro.users_app.Models.User;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
public class UserServiceByList {

    @Autowired
    private Faker faker;

    @Getter
    private List<User> users=new ArrayList<>();

    @PostConstruct
    public void init(){
        IntStream.range(0,100).forEach(i->
                users.add(new User(faker.funnyName().name(),faker.name().name(),faker.pokemon().name()))
        );
    }

    public List<User> getUsers(String startsWith){
        if(Objects.nonNull(startsWith)){
            return users.stream().filter(user -> user.getUserName().startsWith(startsWith)).collect(Collectors.toList());
        }else{
            return users;
        }

    }

    public User getUserByName(String name) {
        return users.stream().filter(u -> u.getUserName().equals(name)).
                findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User %s not found", name)));
    }
    public User createUser(User user){
        if(users.stream().anyMatch(u->u.getUserName().equals(user.getUserName()))){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, String.format("User %s already exists.",user.getUserName()));
        }
        users.add(user);
        return user;
    }

    public void deleteUser(String name){
        User username= getUserByName(name);
        users.remove(username);
    }
}
