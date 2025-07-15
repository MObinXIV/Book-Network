package com.mobin.book.history;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookTransactionHistoryRepository  extends JpaRepository<BookTransactionHistory, UUID> {

    @Query(

            """
        SELECT history FROM BookTransactionHistory history
        WHERE history.user.id = :userId
"""
    )
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, UUID userId);
    @Query(

            """
        SELECT history FROM BookTransactionHistory history
        WHERE history.book.owner.id = :userId
"""
    )
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, UUID userId);

    @Query("""
        SELECT
        (COUNT(*)>0) AS isBorrowed
        FROM BookTransactionHistory bookTransactionHistory
        WHERE bookTransactionHistory.user.id = :userId
        AND bookTransactionHistory.book.id = :bookId
        AND bookTransactionHistory.returnApproved = false
     """
    )
    boolean isAlreadyBorrowedByUser(UUID bookId, UUID userId);

    @Query(

            """
        SELECT transaction FROM BookTransactionHistory transaction
        WHERE transaction.book.id = :bookId AND transaction.user.id = :userId
         AND transaction.returnApproved = false
         AND transaction.returned = false
"""
    )
     Optional <BookTransactionHistory> findByBookIdAndUserId(UUID bookId, UUID userId);

    @Query(

            """
        SELECT transaction FROM BookTransactionHistory transaction
        WHERE transaction.book.owner.id = :bookId AND transaction.user.id = :userId
         AND transaction.returnApproved = false
         AND transaction.returned = true
"""
    )
    Optional <BookTransactionHistory> findByBookIdAndOwnerId(UUID bookId, UUID id);
}
