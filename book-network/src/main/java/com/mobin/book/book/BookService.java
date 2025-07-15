package com.mobin.book.book;


import com.mobin.book.common.PageResponse;
import com.mobin.book.exception.OperationNotPermittedException;
import com.mobin.book.file.FileServiceStorage;
import com.mobin.book.history.BookTransactionHistory;
import com.mobin.book.history.BookTransactionHistoryRepository;
import com.mobin.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository transactionHistoryRepository;
    private final FileServiceStorage fileServiceStorage;
    public BookService(BookMapper bookMapper, BookRepository bookRepository, BookTransactionHistoryRepository transactionHistoryRepository, FileServiceStorage fileServiceStorage) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.fileServiceStorage = fileServiceStorage;
    }

    public UUID save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(UUID bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books= bookRepository.findAllDisplayableBooks( pageable , user.getId());
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );

    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books= bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<BookResponse> bookResponses = books.stream()
                .map(bookMapper::toResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable,user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = transactionHistoryRepository.findAllReturnedBooks(pageable,user.getId());
        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponses,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalElements(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.isFirst(),
                allBorrowedBooks.isLast()
        );
    }

    public UUID updateSharableStatus(UUID bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(book.getOwner().getId(), user.getId())) {
          throw new OperationNotPermittedException("You can't update others sharable status");
        }
        book.setShareable(!book.isShareable());// Toggle the shareable status
        bookRepository.save(book);
        return book.getId();
    }

    public UUID updateArchivedStatus(UUID bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can't update others archived status");
        }
        book.setArchived(!book.isArchived());// Toggle the shareable status
        bookRepository.save(book);
        return book.getId();
    }

    public UUID borrowBook(UUID bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        if(book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book can't be borrowed since it is either archived or not shareable");
        }

        User user = ((User) connectedUser.getPrincipal());
        // The owner of the book can't borrow it
        if(Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can't borrow your own book");
        }

        /// check if the book is already borrowed
        final boolean isAlreadyBorrowed = transactionHistoryRepository.isAlreadyBorrowedByUser(bookId,user.getId());
        if(isAlreadyBorrowed) {
            throw new OperationNotPermittedException("The requested book is already borrowed ");
        }
        BookTransactionHistory transactionHistory = new BookTransactionHistory();
        transactionHistory.setBook(book);
        transactionHistory.setUser(user);
        transactionHistory.setReturned(false);
        transactionHistory.setReturnApproved(false);
        return transactionHistoryRepository.save(transactionHistory).getId();
    }

    public UUID returnBorrowedBook(UUID bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        if(book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book can't be borrowed since it is either archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        // The owner of the book can't borrow it
        if(Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can't borrow or return your own book");
        }
        BookTransactionHistory transactionHistory = transactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You can't return a book that you haven't borrowed"));
        transactionHistory.setReturned(true);
        return transactionHistoryRepository.save(transactionHistory).getId();

    }

    public UUID approveReturnBorrowedBook(UUID bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        if(book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book can't be borrowed since it is either archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        // The owner of the book can't borrow it
        if(Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can't borrow or return your own book");
        }
        BookTransactionHistory transactionHistory = transactionHistoryRepository.findByBookIdAndOwnerId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The book is not returned yet. You can't approve"));
        transactionHistory.setReturned(true);
        return transactionHistoryRepository.save(transactionHistory).getId();
    }


    public void uploadBookCoverPicture(UUID bookId, MultipartFile file, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        User user = ((User) connectedUser.getPrincipal());
        var bookCover = fileServiceStorage.saveFile(file,user.getId());
        book.setBookCover(bookCover);

    }
}
