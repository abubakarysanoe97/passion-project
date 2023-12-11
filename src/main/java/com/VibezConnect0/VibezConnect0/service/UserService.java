package com.VibezConnect0.VibezConnect0.service;

import com.VibezConnect0.VibezConnect0.models.User;
import com.VibezConnect0.VibezConnect0.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public User createUser(User user) {
        User user1 = new User();
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public void editUser(Long userId, User user) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user1 = optionalUser.get();
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            userRepository.save(user1);
        } else {
            // Handle the case where the user with the given ID is not found.
            throw new IllegalArgumentException("User not found");
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}


