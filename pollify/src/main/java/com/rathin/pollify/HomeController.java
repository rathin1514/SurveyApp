package com.rathin.pollify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String login() {
        return "redirect:/login.html";
    }

    @RequestMapping("/register")
    public String register() {
        return "redirect:/register.html";
    }

    @RequestMapping("/home")
    public String home() {
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
