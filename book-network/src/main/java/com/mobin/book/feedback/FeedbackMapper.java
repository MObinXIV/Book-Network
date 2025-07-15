package com.mobin.book.feedback;

import com.mobin.book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setNote(request.note());
        feedback.setComment(request.comment());
        Book book = new Book();
        book.setId(request.bookId());
        feedback.setBook(book);
        return feedback;
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, UUID id) {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setNote(feedback.getNote());
        feedbackResponse.setComment(feedback.getComment());
        feedbackResponse.setOwnFeedback(Objects.equals(feedback.getCreatedBy(),id ));
        return feedbackResponse;
    }
}
