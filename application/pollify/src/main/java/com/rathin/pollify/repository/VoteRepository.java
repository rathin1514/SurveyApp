package com.rathin.pollify.repository;

import com.rathin.pollify.entity.Vote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByQuestionId(Long questionId);
}