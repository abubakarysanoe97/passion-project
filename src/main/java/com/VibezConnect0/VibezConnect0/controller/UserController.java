package com.VibezConnect0.VibezConnect0.controller;

import com.VibezConnect0.VibezConnect0.exceptions.ResourceNotFoundException;
import com.VibezConnect0.VibezConnect0.models.User;
import com.VibezConnect0.VibezConnect0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    protected void verifyUser(Long userId) throws ResourceNotFoundException {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }




    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(user.getId()).toUri();
        responseHeaders.setLocation(newUserUri);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }

    @GetMapping("user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        return optionalUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<String> editUser(@RequestBody User username, @PathVariable Long userId) {
        try {
            userService.editUser(userId,username);
            return new ResponseEntity<>("User edited successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error editing user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        verifyUser(userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
