package com.rathin.pollify.repository;

import com.rathin.pollify.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // You can add custom queries if needed later
}