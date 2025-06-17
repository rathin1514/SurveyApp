package com.rathin.pollify.service;

import com.rathin.pollify.dto.VoteRequest;
import com.rathin.pollify.dto.VoteSubmission;
import com.rathin.pollify.entity.Question;
import com.rathin.pollify.entity.Vote;
import com.rathin.pollify.repository.QuestionRepository;
import com.rathin.pollify.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void submitVotes(VoteSubmission voteSubmission) {
        for (VoteRequest voteRequest : voteSubmission.getVotes()) {
            Question question = questionRepository.findById(voteRequest.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + voteRequest.getQuestionId()));

            Vote vote = new Vote();
            vote.setQuestion(question);
            vote.setVote(voteRequest.isVote());

            voteRepository.save(vote);
        }
    }
}