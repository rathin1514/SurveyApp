package com.rathin.pollify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rathin.pollify.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
