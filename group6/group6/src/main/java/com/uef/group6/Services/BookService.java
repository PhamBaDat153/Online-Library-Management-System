package com.uef.group6.Services;

import com.uef.group6.Methods.Exceptions.BadRequestException;
import com.uef.group6.Methods.Exceptions.IDNotFoundException;
import com.uef.group6.Models.Book;
import com.uef.group6.Repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Thêm 1 cuốn sách vào DB
    public Book addBook(Book book) {
        Book savedBook = new Book();
        // Kiểm tra xem tên cuốn sách có trống không
        if (book.getBookName().isEmpty()) {
            throw new BadRequestException("Không thể thêm cuốn sách vì để trống tên!!");
        }
        else {
            savedBook = book;
        }
        return bookRepository.save(savedBook);
    }

    //Lấy tất cả các cuốn sách trong DB
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //Tìm một cuốn sách theo ID
    public Book getBookById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new IDNotFoundException("Sách với ID: " + bookId + " không tồn tại"));
    }

    //Tìm 1 cuốn sách theo tên
    public List<Book> getBookByName(String bookName) {
        List<Book> books = bookRepository.findAllByBookNameContainingIgnoreCase(bookName);
        if (books.isEmpty()) {
            throw new IDNotFoundException("Không tìm thấy cuốn sách với tên: " + bookName);
        }
        return books;
    }

    //Cập nhật 1 cuốn sách
    public Book updateBook(Book book, String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookUpdate;
        //Kiểm tra ID cuốn sách có tồn tại không
        if (bookOptional.isPresent()) {
            bookUpdate = bookOptional.get();
            // Kiểm tra xem tên cuốn sách có trống không
            if (book.getBookName() == null || book.getBookName().isEmpty()) {
                throw new BadRequestException("Không thể cập nhật thông cuốn sách vì để trống tên!!");
            }
            bookUpdate.setBookName(book.getBookName());
            bookUpdate.setBookState(book.getBookState());
        } else {
            throw new IDNotFoundException("Sách với ID: " + id + " không tồn tại!!");
        }
        return bookRepository.save(bookUpdate);
    }

    //Xóa 1 cuốn sách
    public void deleteBook(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (!bookOptional.isPresent()) {
            throw new IDNotFoundException("Sách với ID: " + id + " không tồn tại!!");
        }
        else {
            bookRepository.deleteById(id);
        }
    }
}
