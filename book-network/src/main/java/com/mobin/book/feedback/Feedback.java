package com.mobin.book.feedback;

import com.mobin.book.book.Book;
import com.mobin.book.common.BaseAuditingEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Feedback  extends BaseAuditingEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private Double note; // 1->5
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private String lastModifiedBy;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;



    public Feedback() {
    }

    public Feedback(LocalDateTime createdDate, LocalDateTime lastModifiedDate, UUID id, Double note, String createdBy, String lastModifiedBy, String comment, Book book) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.note = note;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.comment = comment;
        this.book = book;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
