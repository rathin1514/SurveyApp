package com.rathin.pollify.controller;

import com.rathin.pollify.dto.RegisterRequest;
import com.rathin.pollify.entity.User;
import com.rathin.pollify.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();

       if (userRepository.findByUsername(username).isPresent()) {
        return "Username already taken!";
    }

    User user = new User(username, password);
    userRepository.save(user);

    return "User registered successfully!";
    }
}
