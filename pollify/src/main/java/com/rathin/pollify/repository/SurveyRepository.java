package com.rathin.pollify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rathin.pollify.entity.Survey;
import com.rathin.pollify.entity.User;

    public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByUser(User user);
}

