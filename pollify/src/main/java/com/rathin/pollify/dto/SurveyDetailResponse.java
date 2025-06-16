package com.rathin.pollify.dto;

import java.util.List;

public class SurveyDetailResponse {
    private String title;
    private List<String> questions;

    public SurveyDetailResponse() {}

    public SurveyDetailResponse(String title, List<String> questions) {
        this.title = title;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}
