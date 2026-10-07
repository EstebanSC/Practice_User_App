package com.esc.pro.users_app.Services;

import com.esc.pro.users_app.Repositories.RoleRepository;
import com.esc.pro.users_app.Repositories.UserInRoleRepository;
import com.esc.pro.users_app.Repositories.UserRepository;
import com.esc.pro.users_app.entities.Role;
import com.esc.pro.users_app.entities.User;
import com.esc.pro.users_app.entities.UserInRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserInRoleRepository userInRoleRepository;

    public Page<User> getUsers(int page, int size) {
        return  userRepository.findAll(PageRequest.of(page, size));
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %d not found", userId)));
    }

    @Cacheable("users")
    public User getUserByUserName(String userName) {
        log.info("Getting user by userName {}", userName);
        return userRepository.findByUserName(userName).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with username : %s not found", userName)));
    }

    @CacheEvict("users")
    public void deleteUserByUserName(String userName) {
        User user = getUserByUserName(userName);
        userRepository.delete(user);
    }

    public Page<String> getUserNames(int page, int size) {
        return userRepository.findUserNames(PageRequest.of(page,size));
    }

    public UserInRole asignRoleToUser(Integer roleId, Integer userId) {
        User user = getUserById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent()){
            UserInRole userInRole = new UserInRole();
            userInRole.setUser(user);
            userInRole.setRole(role.get());
            return userInRoleRepository.save(userInRole);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role not fount with Id: %d", roleId));
        }
    }

    public List<User> getUsersByRole(Integer roleId) {
        Optional<List<UserInRole>> usersRole = userInRoleRepository.getUserInRoleByRoleId(roleId);
        List<User> users= new ArrayList<>();
        if(usersRole.isPresent()){
            usersRole.get().stream().forEach(t->users.add(t.getUser()));
            return users;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("There are not any user with Role ID: %d",roleId));
        }
    }

}
