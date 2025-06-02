package com.uef.group6.Repositories;

import com.uef.group6.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    //Tìm sách bằng tên
    List<Book> findAllByBookNameContainingIgnoreCase(String bookName);
}