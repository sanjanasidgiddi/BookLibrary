package com.revature.library.Service;

import com.revature.library.DAO.BookDAO;
import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookLogService{
    private final BookLogDAO dao;
    private final BookDAO bookDAO;

    @Autowired
    public BookLogService(BookLogDAO dao, BookDAO bookDAO) {
        this.dao = dao;
        this.bookDAO=bookDAO;
    }

    public static class InvalidReturnDate extends Exception{}
    public static class BookAlreadyHeld extends Exception{}

    public BookLog issue(BookLog newLog) throws BookAlreadyHeld, InvalidReturnDate {
        //overwrites previous data
        //TODO might cause issues
        var returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DATE, 30);

        newLog.setDateIssued(new Date());
        newLog.setDateIssued(returnDate.getTime());
        newLog.setDateToBeReturned(null);

        if (isBookHeld(newLog.getBook().getBookId())){
            throw new BookAlreadyHeld();
        }

        if (!newLog.getDateIssued().before(newLog.getDateToBeReturned())){
            throw new InvalidReturnDate();
        }

        return dao.save(newLog);
    }

    public Optional<BookLog> get(int id){
        return dao.findById(id);
    }

    public List<BookLog> getAll(){
        return dao.findAll();
    }

    public List<BookLog> getAll(String username){
        return dao.findByUser_Username(username);
    }

    public Optional<User> getAllBookByUser(String username) {
        // TODO Auto-generated method stub
        return dao.findBookByUser_Username(username);
    }

    public void returnBook(int id) throws NotFound {
        var log = dao.findById(id).orElseThrow(()->new NotFound());

        log.setDateActuallyReturned(new Date());

        dao.save(log);
    }

    public void edit(int id, BookLog newBookLog) throws NotFound {
        var logInTable = dao.findById(id).orElseThrow(()->new NotFound());

        logInTable.setBook(newBookLog.getBook());
        logInTable.setUser(newBookLog.getUser());
        logInTable.setDateIssued(newBookLog.getDateIssued());
        logInTable.setDateToBeReturned(newBookLog.getDateToBeReturned());
        logInTable.setDateActuallyReturned(logInTable.getDateActuallyReturned());

        dao.save(logInTable);
    }

    public void delete(int id) throws NotFound {
        if (dao.existsById(id)){
            throw new NotFound();
        }

        dao.deleteById(id);
    }

    public static class NotFound extends Exception{}

    boolean isBookHeld(int bookId){
        return dao.findByBook_BookId(bookId)
            .stream()
            .anyMatch(it->it.getDateActuallyReturned() == null);
    }
}