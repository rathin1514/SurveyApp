package com.rathin.pollify.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rathin.pollify.entity.SurveyLink;

public interface SurveyLinkRepository extends JpaRepository<SurveyLink, Long> {
    Optional<SurveyLink> findByLink(String link);
    Optional<SurveyLink> findBySurvey_Id(Long surveyId);
}
