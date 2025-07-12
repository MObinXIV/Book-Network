package com.mobin.book.feedback;

import com.mobin.book.book.Book;
import com.mobin.book.common.BaseAuditingEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Feedback  extends BaseAuditingEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private Double note; // 1->5

    private String comment;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
