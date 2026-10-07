package com.esc.pro.users_app.Controllers;

import com.esc.pro.users_app.Services.UserService;
import com.esc.pro.users_app.entities.User;
import com.esc.pro.users_app.entities.UserInRole;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserConroller {

    public record UserInRoleIds (Integer roleId, Integer userId){};

    @Autowired
    private UserService userService;

    @GetMapping
    @Timed("get.users")
    @Operation( summary = "Get Users Created",
            description = "Returns a list of users created.",
    responses = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Users don't exist.")
    })
    public ResponseEntity<Page<User>> getUsers(@RequestParam(required = false, value = "page", defaultValue = "1") int page,
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

    @DeleteMapping("/userName/{userName}")
    public  ResponseEntity<Void> deleteUser(@PathVariable("userName") String userName) {
        userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/assignRole")
    public ResponseEntity<UserInRole> assignRoleToUser(@RequestBody UserInRoleIds userInRoleIds) {
        return new ResponseEntity<>(userService.asignRoleToUser(userInRoleIds.roleId,userInRoleIds.userId),HttpStatus.CREATED);
    }

    @GetMapping("/roles/{roleId}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable("roleId") Integer roleId){
        return new ResponseEntity<>(userService.getUsersByRole(roleId),HttpStatus.OK);
    }

}
