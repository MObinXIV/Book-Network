package com.mobin.book.book;

import com.mobin.book.file.FileUtils;
import com.mobin.book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {
    public Book toBook(BookRequest request) {
        Book book = new Book();
        book.setId(request.id());
        book.setTitle(request.title());
        book.setAuthorName(request.authorName());
        book.setSynopsis(request.synopsis());
        book.setIsbn(request.isbn());
        book.setArchived(false);
        return book;
    }

    public BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthorName(book.getAuthorName());
        response.setIsbn(book.getIsbn());
        response.setSynopsis(book.getSynopsis());
        response.setOwner(book.getOwner().getFullName());
        response.setArchived(book.isArchived());
        response.setShareable(book.isShareable());
        response.setRate(book.getRate());
        response.setCover(FileUtils.readFileFromLocation(book.getBookCover()));
        return response;

    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        BorrowedBookResponse response = new BorrowedBookResponse();
        response.setId(history.getBook().getId());
        response.setTitle(history.getBook().getTitle());
        response.setAuthorName(history.getBook().getAuthorName());
        response.setIsbn(history.getBook().getIsbn());
        response.setRate(history.getBook().getRate());
        response.setReturned(history.isReturned());
        response.setReturnApproved(history.isReturnApproved());
        return response;
    }
}
