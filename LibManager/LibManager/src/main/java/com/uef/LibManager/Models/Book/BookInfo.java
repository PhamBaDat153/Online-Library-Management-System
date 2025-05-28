package com.uef.LibManager.Models.Book;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "BookInfo")
public class BookInfo {

    @Id
    @Column(name = "bookInfoID")
    @GeneratedValue(generator = "bookinfo-id-generator")
    @GenericGenerator(name = "bookinfo-id-generator",
            strategy = "com.uef.LibManager.Models.Book.IDgenerator.BookInfoIDGenerator")
    private String bookInfoID;

    @Column(name = "bookName")
    private String bookName;

    @Column(name = "bookGenre")
    private String bookGenre;

    @Column(name = "borrowPrice")
    private Double borrowPrice;

    @Column(name = "bookQuantity")
    private Integer bookQuantity;

    @Column(name = "state")
    private Boolean state;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookInfoID")  // Khóa ngoại trong bảng IndividualBook
    private List<IndividualBook> availableBookList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bookInfoID")
    private List<IndividualBook> borrowedBookList;

    public BookInfo() {
    }

    public BookInfo(String bookName, String bookGenre, Double borrowPrice,
                    Integer bookQuantity, Boolean state,
                    List<IndividualBook> availableBookList,
                    List<IndividualBook> borrowedBookList) {
        this.bookName = bookName;
        this.bookGenre = bookGenre;
        this.borrowPrice = borrowPrice;
        this.bookQuantity = bookQuantity;
        this.state = state;
        this.availableBookList = availableBookList;
        this.borrowedBookList = borrowedBookList;
    }

    // Getter - Setter

    public String getBookInfoID() {
        return bookInfoID;
    }

    public void setBookInfoID(String bookInfoID) {
        this.bookInfoID = bookInfoID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public Double getBorrowPrice() {
        return borrowPrice;
    }

    public void setBorrowPrice(Double borrowPrice) {
        this.borrowPrice = borrowPrice;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public List<IndividualBook> getAvailableBookList() {
        return availableBookList;
    }

    public void setAvailableBookList(List<IndividualBook> availableBookList) {
        this.availableBookList = availableBookList;
    }
    public List<IndividualBook> getBorrowedBookList() {
        return borrowedBookList;
    }

    public void setBorrowedBookList(List<IndividualBook> borrowedBookList) {
        this.borrowedBookList = borrowedBookList;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "bookInfoID='" + bookInfoID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookGenre='" + bookGenre + '\'' +
                ", borrowPrice=" + borrowPrice +
                ", bookQuantity=" + bookQuantity +
                ", state=" + state +
                ", availableBookList=" + availableBookList +
                ", borrowedBookList=" + borrowedBookList +
                '}';
    }
}