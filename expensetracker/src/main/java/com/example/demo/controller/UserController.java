package com.example.demo.controller;

import java.util.Optional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ User signup (register)
    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        try {
            // Check if user already exists
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT); // 409 Conflict
            }

            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());

            User savedUser = userRepository.save(newUser);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    // ✅ User login
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginUser) {
        try {
            Optional<User> user = userRepository.findByEmailAndPassword(
                loginUser.getEmail(), loginUser.getPassword());

            if (user.isPresent()) {
                return new ResponseEntity<>(user.get(), HttpStatus.OK); // 200 OK
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
