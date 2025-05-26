package com.uef.LibManager.Models.Book;

import com.uef.LibManager.Methods.DateCalculate;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "IndividualBook")
public class IndividualBook {

    @Id
    @Column(name = "bookID")
    @GeneratedValue(generator = "book-id-generator")
    @GenericGenerator(name = "book-id-generator",
            strategy = "com.uef.LibManager.Models.Book.IDgenerator.BookIDGenerator")
    private String bookID;

    @Column(name = "bookName")
    private String bookName;

    @Column(name = "borrowTime")
    private int borrowTime;

    @Column(name = "borrowDate")
    private Date borrowDate;

    @Column(name = "returnDate")
    private Date returnDate;

    //xxx.xxx đ / ngày
    @Column(name = "borrowPrice")
    private Double borrowPrice;

    // totalPrice = borrowPrice * borrowTime
    @Column(name = "totalPrice")
    private Double totalPrice;

    @Column(name = "state")
    private Boolean state;

    //Constructor
    public IndividualBook(String bookName,
                          int borrowTime,
                          Date borrowDate,
                          Double borrowPrice,
                          Boolean state) {
        this.bookName = bookName;
        this.borrowTime = borrowTime;
        this.borrowDate = borrowDate;
        this.borrowPrice = borrowPrice;
        this.state = state;
        this.returnDate = DateCalculate.addDays(borrowDate, borrowTime);
        this.totalPrice = borrowPrice*borrowTime;
    }

    public IndividualBook(String bookName,
                          Double borrowPrice) {
        this.bookName = bookName;
        this.borrowPrice = borrowPrice;
        this.state = true;
    }



    public IndividualBook() {
    }

    //Getter & Setter

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

    public int getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(int borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getBorrowPrice() {
        return borrowPrice;
    }

    public void setBorrowPrice(Double borrowPrice) {
        this.borrowPrice = borrowPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }


    //To String
    @Override
    public String toString() {
        return "IndividualBook{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", borrowTime=" + borrowTime +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", borrowPrice=" + borrowPrice +
                ", totalPrice=" + totalPrice +
                ", state=" + state +
                '}';
    }
}
