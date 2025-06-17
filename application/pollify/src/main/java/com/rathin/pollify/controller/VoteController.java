package com.rathin.pollify.controller;

import com.rathin.pollify.dto.VoteSubmission;
import com.rathin.pollify.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitVotes(@RequestBody VoteSubmission voteSubmission) {
        try {
            voteService.submitVotes(voteSubmission);
            return ResponseEntity.ok("Votes submitted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while submitting votes");
        }
    }
}
