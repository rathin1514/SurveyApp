package com.rathin.pollify.controller;

import com.rathin.pollify.entity.Question;
import com.rathin.pollify.entity.Survey;
import com.rathin.pollify.entity.Vote;
import com.rathin.pollify.repository.QuestionRepository;
import com.rathin.pollify.repository.SurveyRepository;
import com.rathin.pollify.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final VoteRepository voteRepository;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("ResultController is up");
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<?> getSurveyResults(@PathVariable Long surveyId) {
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);

        if (surveyOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Survey not found");
        }

        Survey survey = surveyOpt.get();
        List<Question> questions = questionRepository.findBySurveyId(surveyId);

        List<QuestionResult> results = questions.stream().map(question -> {
            List<Vote> votes = voteRepository.findByQuestionId(question.getId());
            long yesVotes = votes.stream().filter(Vote::isVote).count();
            long noVotes = votes.size() - yesVotes;

            return new QuestionResult(
                    question.getId(),
                    question.getQuestion(),
                    yesVotes,
                    noVotes,
                    votes.size());
        }).collect(Collectors.toList());

        return ResponseEntity.ok(new SurveyResult(survey.getTitle(), results));
    }

    @Data
    @AllArgsConstructor
    static class SurveyResult {
        private String title;
        private List<QuestionResult> results;
    }

    @Data
    @AllArgsConstructor
    static class QuestionResult {
        private Long questionId;
        private String questionText;
        private long yesVotes;
        private long noVotes;
        private long totalVotes;
    }
}
