package com.revature.library.Models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="book_logs")
public class BookLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int BookLogId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="username")
    private User user;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="BookId")
    private Book book;

    @Column(name="date_issued")
    private Date dateIssued;

    @Column(name="date_to_be_returned")
    private Date dateToBeReturned;

    @Column(name="date_actually_returned")
    private Date dateActuallyReturned;

    public BookLog(){}

    public BookLog(User user, Book book, int BookLogId, Date dateIssued, Date dateToBeReturned, Date dateActuallyReturned){
        this.user=user;
        this.book=book;
        this.BookLogId=BookLogId;
        this.dateIssued=dateIssued;
        this.dateToBeReturned=dateToBeReturned;
        this.dateActuallyReturned=dateActuallyReturned;
    }

    public int getBookLogId() {
        return BookLogId;
    }

    public void setBookLogId(int bookLogId) {
        BookLogId = bookLogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getDateToBeReturned() {
        return dateToBeReturned;
    }

    public void setDateToBeReturned(Date dateToBeReturned) {
        this.dateToBeReturned = dateToBeReturned;
    }

    public Date getDateActuallyReturned() {
        return dateActuallyReturned;
    }

    public void setDateActuallyReturned(Date dateActuallyReturned) {
        this.dateActuallyReturned = dateActuallyReturned;
    }
    
}
