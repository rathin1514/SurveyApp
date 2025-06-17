package com.rathin.pollify.dto;

public class VoteRequest {
    private Long questionId;
    private boolean vote;

    public VoteRequest() {
    }

    public VoteRequest(Long questionId, boolean vote) {
        this.questionId = questionId;
        this.vote = vote;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public boolean isVote() {
        return vote;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }
}
