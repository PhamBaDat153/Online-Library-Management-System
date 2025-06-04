package com.uef.LibraryManagementWeb.Repository;

import com.uef.LibraryManagementWeb.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByBookNameContainingIgnoreCase(String bookName);

    List<Book> findByAvailible(boolean availible);

    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);
}