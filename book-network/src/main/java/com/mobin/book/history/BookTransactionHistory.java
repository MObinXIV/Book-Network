package com.mobin.book.history;

import com.mobin.book.book.Book;
import com.mobin.book.common.BaseAuditingEntity;
import com.mobin.book.user.User;
import jakarta.persistence.*;

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

}
