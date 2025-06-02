package com.uef.group6.Controllers;


import com.uef.group6.Models.Book;
import com.uef.group6.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Thêm 1 cuốn sách vào DB
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(new Book(book.getBookName(), true)));
    }

    //Lấy tất cả các cuốn sách trong DB
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    //Tìm sách bằng tên
    @GetMapping("/book/name/{bookName}")
    public ResponseEntity<List<Book>> getBookByName(@PathVariable String bookName) {
        return ResponseEntity.ok(bookService.getBookByName(bookName));
    }

    //Tìm sách bằng ID
    @GetMapping("/book/{bookID}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookID") String bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    //Cập nhật 1 cuốn sách
    @PutMapping("/book/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable String bookId, @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book, bookId));
    }

    //Xóa 1 cuốn sách
    @DeleteMapping("/book/{bookId}")
    public void deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
    }
}