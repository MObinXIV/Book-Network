package com.mobin.book.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    @Query(
            """
           SELECT feedback
           FROM Feedback feedback
           WHERE feedback.book.id = :bookId
           """
    )
    Page<Feedback> findAllByBookId(UUID bookId, Pageable pageable);
}
