package com.rathin.pollify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String login(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("username") != null) {
        return "redirect:/home";
    }
    return "redirect:/login.html";
    }

    @RequestMapping("/register")
    public String register() {
        return "redirect:/register.html";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            
            return "redirect:/login.html";
        }
        return "redirect:/user.html";
    }

    @RequestMapping("/new")
    public String createSurvey() {
        return "redirect:/create.html";
    }

    @RequestMapping("/result")
    public String showResults() {
        return "redirect:/results.html";
    }

    @RequestMapping("/join")
    public String startSurvey() {
        return "redirect:/join.html";
    }
}
