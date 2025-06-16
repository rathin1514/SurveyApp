package com.rathin.pollify.controller;

import com.rathin.pollify.dto.LoginRequest;
import com.rathin.pollify.dto.RegisterRequest;
import com.rathin.pollify.entity.User;
import com.rathin.pollify.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

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

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Optional<User> foundUser = userRepository.findByUsername(loginRequest.getUsername());
        if (foundUser.isPresent() && foundUser.get().getPassword().equals(loginRequest.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", foundUser.get().getId());
            session.setAttribute("username", foundUser.get().getUsername());
            return "Login successful!";
        }
            return "Invalid credentials!";
        }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "Logout successful!";
    }

    @GetMapping("/me")
    public Object getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return new org.springframework.http.ResponseEntity<>("Not logged in", org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        if (userId == null || username == null) {
            return new org.springframework.http.ResponseEntity<>("Not logged in", org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("userId", userId);
        map.put("username", username);
        return map;
    }
}
