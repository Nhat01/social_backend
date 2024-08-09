package com.nhat.social.controller;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.User;
import com.nhat.social.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() throws UserException {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        return new ResponseEntity<>(userService.findUserProfileByJwt(jwt), HttpStatus.OK);
    }

    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@RequestBody User user) throws UserException {
        User createUser = userService.registerUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/update")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String jwt,@RequestBody User user) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User updatedUser = userService.updateUserDetails(user,reqUser.getId());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/users/follow/{userId2}")
    public ResponseEntity<User> followUser(@RequestHeader("Authorization") String jwt,@PathVariable Integer userId2) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        User user = userService.followUser(reqUser.getId(), userId2);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/users/search")
    public ResponseEntity<Set<User>> getUserById(@RequestParam("query") String query) throws UserException {
        Set<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
