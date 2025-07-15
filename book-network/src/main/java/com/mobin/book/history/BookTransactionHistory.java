package com.mobin.book.history;

import com.mobin.book.book.Book;
import com.mobin.book.common.BaseAuditingEntity;
import com.mobin.book.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class BookTransactionHistory extends BaseAuditingEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private boolean returned;
    private boolean returnApproved;

    public BookTransactionHistory(LocalDateTime createdDate, LocalDateTime lastModifiedDate, UUID id, User user, Book book, boolean returned, boolean returnApproved) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.user = user;
        this.book = book;
        this.returned = returned;
        this.returnApproved = returnApproved;
    }

    public BookTransactionHistory() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isReturnApproved() {
        return returnApproved;
    }

    public void setReturnApproved(boolean returnApproved) {
        this.returnApproved = returnApproved;
    }
}
