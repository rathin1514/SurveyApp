package com.rathin.pollify.dto;

import java.util.List;

public class CreateSurveyRequest {
    private String title;
    private List<String> questions;

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
