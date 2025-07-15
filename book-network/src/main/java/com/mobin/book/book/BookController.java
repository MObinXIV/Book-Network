package com.mobin.book.book;

import com.mobin.book.common.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("books")
@Tag(name="Book")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<UUID> saveBook(@Valid @RequestBody BookRequest request,
                                         Authentication connectedUser
    ){
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("/{book-id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable("book-id") UUID bookId) {
        return ResponseEntity.ok(service.findById(bookId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name="page",defaultValue = "0",required = false) int page,
            @RequestParam(name="size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ){
     return ResponseEntity.ok(service.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name="page",defaultValue = "0",required = false) int page,
            @RequestParam(name="size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name="page",defaultValue = "0",required = false) int page,
            @RequestParam(name="size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name="page",defaultValue = "0",required = false) int page,
            @RequestParam(name="size",defaultValue = "10",required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/sharable/{book-id}")
    public ResponseEntity<UUID> updateSharableStatus(@PathVariable("book-id") UUID bookId,
                                                     Authentication connectedUser) {
        return ResponseEntity.ok(service.updateSharableStatus(bookId, connectedUser));

    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<UUID> updateArchivedStatus(@PathVariable("book-id") UUID bookId,
                                                     Authentication connectedUser) {
        return ResponseEntity.ok(service.updateArchivedStatus(bookId, connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<UUID> borrowBook(@PathVariable("book-id") UUID bookId,
                                           Authentication connectedUser) {
        return ResponseEntity.ok(service.borrowBook(bookId, connectedUser));
    }

    @PostMapping("/borrow/return/{book-id}")
    public ResponseEntity<UUID> returnBorrowBook(@PathVariable("book-id") UUID bookId,
                                           Authentication connectedUser) {
        return ResponseEntity.ok(service.returnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<UUID> approveReturnBorrowBook(@PathVariable("book-id") UUID bookId,
                                                 Authentication connectedUser) {
        return ResponseEntity.ok(service.approveReturnBorrowedBook(bookId, connectedUser));
    }
    @PostMapping(value = "/cover/{book-id}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") UUID bookId,
            @RequestPart("file") MultipartFile file,
            @Parameter()
            Authentication connectedUser
    ) {

        service.uploadBookCoverPicture(bookId, file, connectedUser);
        return ResponseEntity.accepted().build();
    }
}
