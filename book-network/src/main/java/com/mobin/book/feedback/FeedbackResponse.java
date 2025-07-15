package com.mobin.book.feedback;

public class FeedbackResponse {
    private Double note;
    private String comment;
    private boolean ownFeedback;

    public FeedbackResponse(Double note, String comment, boolean ownFeedback) {
        this.note = note;
        this.comment = comment;
        this.ownFeedback = ownFeedback;
    }

    public FeedbackResponse() {
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isOwnFeedback() {
        return ownFeedback;
    }

    public void setOwnFeedback(boolean ownFeedback) {
        this.ownFeedback = ownFeedback;
    }
}
