package com.rathin.pollify.dto;

import java.util.List;

public class SurveyDetailResponse {
    private String title;
    private List<QuestionResponse> questions;

    public SurveyDetailResponse() {}

    public SurveyDetailResponse(String title, List <QuestionResponse> questions) {
        this.title = title;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResponse> questions) {
        this.questions = questions;
    }
}
