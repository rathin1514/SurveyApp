package com.rathin.pollify.dto;

import java.util.List;

public class VoteSubmission {
     private List<VoteRequest> votes;

     public VoteSubmission() {
    }

    public VoteSubmission(List<VoteRequest> votes) {
        this.votes = votes;
    }

    public List<VoteRequest> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteRequest> votes) {
        this.votes = votes;
    }
}
