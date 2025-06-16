package com.rathin.pollify.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rathin.pollify.dto.CreateSurveyRequest;
import com.rathin.pollify.entity.Question;
import com.rathin.pollify.entity.Survey;
import com.rathin.pollify.entity.User;
import com.rathin.pollify.repository.SurveyRepository;
import com.rathin.pollify.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {
    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createSurvey(@RequestBody CreateSurveyRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in.");
        }

        String title = request.getTitle();
        List<String> questions = request.getQuestions();

        User user = userRepository.findById(userId).orElseThrow();
        Survey survey = new Survey();
        survey.setTitle(title);
        survey.setUser(user);
        survey.setCreatedAt(LocalDateTime.now());

        // Create and add questions
        List<Question> questionEntities = questions.stream().map(q -> {
            Question question = new Question();
            question.setQuestion(q);
            question.setSurvey(survey);
            return question;
        }).collect(Collectors.toList());
        survey.setQuestions(questionEntities);

        surveyRepository.save(survey);

        return ResponseEntity.ok("Survey created!");
    }
    
    @GetMapping("/user")
    public ResponseEntity<?> getUserSurveys(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        List<Survey> surveys = surveyRepository.findByUserId(userId);

        List<Map<String, Object>> surveyList = surveys.stream().map(survey -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", survey.getId());
            map.put("title", survey.getTitle());
            map.put("createdAt", survey.getCreatedAt().toString());
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("surveys", surveyList);

        return ResponseEntity.ok(response);
    }
}
