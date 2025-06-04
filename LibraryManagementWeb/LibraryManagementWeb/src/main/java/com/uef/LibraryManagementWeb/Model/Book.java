package com.uef.LibraryManagementWeb.Model;

import com.uef.LibraryManagementWeb.UsefulMethod.IDgenerator.BookIdGenerator;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "book")
public class Book {

    //Thuộc tính
    @Id
    @Column(name = "book_id")
    private String bookID;

    @Column(name = "book_name")
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String bookName;

    @Column(name = "author_name")
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String authorName;

    @Column(name = "book_description")
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String bookDescription;

    @Column(name = "number_of_copy")
    private Integer numberOfCopy;

    @Column(name = "rent_price")
    private Double rentPrice;

    @Column(name = "availible")
    private Boolean availible;

    @Column(name = "book_cover")
    private String bookCoverpath;

    //Constructor
    public Book() {
    }

    public Book(String bookName,
                String authorName,
                String bookDescription,
                Integer numberOfCopy,
                Double rentPrice) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookDescription = bookDescription;
        this.numberOfCopy = numberOfCopy;
        this.rentPrice = rentPrice;
        this.availible = isAvailable(numberOfCopy);

    }

    //Tự tạo Id mỗi khi sách mới đưọc thêm vào DB
    @PrePersist
    private void generateBookId() {
        if (bookID == null || bookID.isEmpty()) {
            this.bookID = BookIdGenerator.generateNextId();
        }
    }

    //Kiểm tra xem sách có có sẵn hay không
    public Boolean isAvailable(Integer numberOfCopy) {
        if (numberOfCopy > 0) {
            return true;
        }
        return false;
    }

    //Getters and Setters

    public String getBookCoverpath() {
        return bookCoverpath;
    }

    public void setBookCoverpath(String bookCoverpath) {
        this.bookCoverpath = bookCoverpath;
    }

    public Boolean getAvailible() {
        return availible;
    }

    public void setAvailible(Boolean availible) {
        this.availible = availible;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Integer getNumberOfCopy() {
        return numberOfCopy;
    }

    public void setNumberOfCopy(Integer numberOfCopy) {
        this.numberOfCopy = numberOfCopy;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    //toString
    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", bookDescription='" + bookDescription + '\'' +
                ", numberOfCopy=" + numberOfCopy +
                ", rentPrice=" + rentPrice +
                ", availible=" + availible +
                ", bookCover=" + bookCoverpath +
                '}';
    }
}
