package com.rathin.pollify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rathin.pollify.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySurveyId(Long surveyId);
}
