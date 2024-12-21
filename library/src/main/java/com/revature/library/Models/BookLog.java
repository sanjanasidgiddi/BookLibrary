package com.revature.library.Models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="book_logs")
public class BookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookLogId;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
    
    @ManyToOne
    @JoinColumn(name="Book_id")
    private Book book;

    @Column(name="date_issued")
    private Date dateIssued;

    @Column(name="date_to_be_returned") 
    private Date dateToBeReturned;

    @Column(name="date_actually_returned")
    private Date dateActuallyReturned;

    public BookLog(){}

    public BookLog(int BookLogId, User user, Book book, Date dateIssued, Date dateToBeReturned, Date dateActuallyReturned){
        this.user=user;
        this.book=book;
        this.bookLogId=BookLogId;
        this.dateIssued=dateIssued;
        this.dateToBeReturned=dateToBeReturned;
        this.dateActuallyReturned=dateActuallyReturned;
    }

    public int getBookLogId() {
        return bookLogId;
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

    @Override
    public String toString() {
        return "BookLog{" +
                "bookLogId=" + bookLogId +
                ", user=" + user +
                ", book=" + book +
                ", dateIssued=" + dateIssued +
                ", dateToBeReturned=" + dateToBeReturned +
                ", dateActuallyReturned=" + dateActuallyReturned +
                '}';
    }
}
