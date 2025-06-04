package com.uef.LibraryManagementWeb.Controller;

import com.uef.LibraryManagementWeb.Model.Book;
import com.uef.LibraryManagementWeb.Service.BookService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/library")
public class BookController {
    private static final HttpHeaders OK_RESPONSE_HEADERS = null; // Để tránh trùng lặp null
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Phương thức tiện ích để tạo ResponseEntity
    private <T> ResponseEntity<T> createOkResponse(T body) {
        return new ResponseEntity<>(body, OK_RESPONSE_HEADERS, HttpStatus.OK);
    }

    // Thêm một cuốn sách vào cơ sở dữ liệu
    @PostMapping(value = "/book", consumes = "multipart/form-data")
    public ResponseEntity<Book> addBook(@RequestParam("bookName") String bookName,
                                        @RequestParam("authorName") String authorName,
                                        @RequestParam("bookDescription") String bookDescription,
                                        @RequestParam("numberOfCopy") Integer numberOfCopy,
                                        @RequestParam("rentPrice") Double rentPrice,
                                        @RequestParam("bookCover") MultipartFile bookCover) throws IOException {
        Book book = new Book(bookName, authorName, bookDescription, numberOfCopy, rentPrice);
        bookService.addBook(book, bookCover);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // Lấy tất cả sách
    @GetMapping("/book")
    public ResponseEntity<List<Book>> getAllBooks() {
        return createOkResponse(bookService.getAllBooks());
    }

    // Lấy tất cả sách có sẵn
    @GetMapping("/book/available")
    public ResponseEntity<List<Book>> getAllAvailableBooks() {
        return createOkResponse(bookService.getAllAvailableBooks());
    }

    // Lấy sách theo ID
    @GetMapping("/book/search/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") String bookId) {
        return createOkResponse(bookService.getBookById(bookId));
    }

    // Tìm kiếm sách theo tên
    @GetMapping("/book/search/name")
    public ResponseEntity<List<Book>> searchBookByName(@RequestParam("bookName") String bookName) {
        return createOkResponse(bookService.searchBookByName(bookName));
    }

    // Tìm kiếm sách theo tên tác giả
    @GetMapping("/book/search/author")
    public ResponseEntity<List<Book>> searchBookByAuthorName(@RequestParam("authorName") String authorName) {
        return createOkResponse(bookService.searchBookByAuthorName(authorName));
    }

    // Xóa một cuốn sách theo ID
    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable("bookId") String bookId) {
        bookService.deleteBookById(bookId);
        return createOkResponse(null);
    }

    // Cập nhật thông tin sách
    @PutMapping(value = "/book/{bookId}", consumes = "multipart/form-data")
    public ResponseEntity<Book> updateBook(@RequestParam("bookName") String bookName,
                                           @RequestParam("authorName") String authorName,
                                           @RequestParam("bookDescription") String bookDescription,
                                           @RequestParam("numberOfCopy") Integer numberOfCopy,
                                           @RequestParam("rentPrice") Double rentPrice,
                                           @RequestParam("bookCover") MultipartFile bookCover,
                                           @PathVariable("bookId") String bookId) throws IOException {
        Book book = new Book(bookName, authorName, bookDescription, numberOfCopy, rentPrice);
        return new ResponseEntity<>( bookService.updateBook(book, bookCover, bookId), HttpStatus.OK);
    }

    // Giảm số lượng bản sao của một cuốn sách
    @PutMapping("/book/reduce/{bookId}")
    public ResponseEntity<Book> reduceNumberOfCopy(@PathVariable("bookId") String bookId,
                                                   @RequestParam("numberOfCopy") Integer numberOfCopy) throws BadRequestException {
        return createOkResponse(bookService.reduceNumberOfCopy(bookId, numberOfCopy));
    }

    // Tăng số lượng bản sao của một cuốn sách
    @PutMapping("/book/add/{bookId}")
    public ResponseEntity<Book> addNumberOfCopy(@PathVariable("bookId") String bookId,
                                                @RequestParam("numberOfCopy") Integer numberOfCopy) throws BadRequestException {
        return createOkResponse(bookService.addNumberOfCopy(bookId, numberOfCopy));
    }
}

