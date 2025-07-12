package com.mobin.book.book;

import com.mobin.book.common.BaseAuditingEntity;
import com.mobin.book.feedback.Feedback;
import com.mobin.book.history.BookTransactionHistory;
import com.mobin.book.user.User;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Book extends BaseAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String bookCover;
    private boolean archived;
    private boolean shareable;
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private UUID createdBy;
    @LastModifiedBy
    @Column(insertable = false)
    private UUID lastModifiedBy;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    public Book() {
    }

    public Book(LocalDateTime createdDate, LocalDateTime lastModifiedDate, UUID id, String title, String authorName, String isbn, String synopsis, String bookCover, boolean archived, boolean shareable, UUID createdBy, UUID lastModifiedBy, User owner, List<Feedback> feedbacks, List<BookTransactionHistory> histories) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.bookCover = bookCover;
        this.archived = archived;
        this.shareable = shareable;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.owner = owner;
        this.feedbacks = feedbacks;
        this.histories = histories;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(UUID lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<BookTransactionHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<BookTransactionHistory> histories) {
        this.histories = histories;
    }
}
