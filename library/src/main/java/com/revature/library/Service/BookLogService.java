package com.revature.library.Service;

import com.revature.library.DAO.BookDAO;
import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.Role;
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

    public BookLog issueBook(int bookId, Optional<User> loggedIn) throws BookAlreadyHeld, BookNotFound, Unauthorized {
        if (isBookHeld(bookId)){
            throw new BookAlreadyHeld();
        }

        var user = loggedIn
            .orElseThrow(
                ()->new Unauthorized()
            );

        var book = bookDAO
            .findById(bookId)
            .orElseThrow(
                ()-> new BookNotFound()
            );

        var returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DATE, 30);

        var newLog = new BookLog();
        newLog.setUser(user);
        newLog.setBook(book);
        newLog.setDateIssued(new Date());
        newLog.setDateToBeReturned(returnDate.getTime());
        newLog.setDateActuallyReturned(null);

        return dao.save(newLog);
    }

    public BookLog returnBook(int bookLogId, Optional<User> loggedIn) throws Unauthorized, NotFound, BookAlreadyReturned {
        var log = dao
            .findById(bookLogId)
            .orElseThrow(
                ()->new NotFound()
            );

        var authorized = loggedIn.map(user->user.getRole() == Role.ADMIN || log.getUser().equals(user)).orElse(false);
        if (!authorized){
            throw new Unauthorized();
        }

        if (log.getDateActuallyReturned() != null){
            throw new BookAlreadyReturned();
        }

        log.setDateActuallyReturned(new Date());

        dao.save(log);

        return log;
    }

    public BookLog get(int id, Optional<User> loggedIn) throws Unauthorized, NotFound {
        var log = dao
            .findById(id)
            .orElseThrow(
                ()->new NotFound()
            );

        var authorized = loggedIn.map(user->user.getRole() == Role.ADMIN || user.getUsername().equals(log.getUser().getUsername())).orElse(false);
        if (!authorized){
            throw new Unauthorized();
        }

        return log;
    }

    public List<BookLog> getAll(Optional<User> loggedIn) throws Unauthorized {
        var user = loggedIn
            .orElseThrow(
                ()->new Unauthorized()
            );

        if (user.getRole() == Role.ADMIN){
            return dao.findAll();
        }
        return dao.findByUser_Username(user.getUsername());
    }

    public BookLog edit(int id, BookLog newBookLog, Optional<User> loggedIn) throws Unauthorized, NotFound {
        var isAuthorized = loggedIn
            .map(
                user->user.getRole() == Role.ADMIN
            )
            .orElse(false);

        if (!isAuthorized){
            throw new Unauthorized();
        }

        var logInTable = dao
            .findById(id)
            .orElseThrow(
                ()->new NotFound()
            );

        logInTable.setBook(newBookLog.getBook());
        logInTable.setUser(newBookLog.getUser());
        logInTable.setDateIssued(newBookLog.getDateIssued());
        logInTable.setDateToBeReturned(newBookLog.getDateToBeReturned());
        logInTable.setDateActuallyReturned(logInTable.getDateActuallyReturned());

        dao.save(logInTable);

        return logInTable;
    }

    public void delete(int id, Optional<User> loggedIn) throws Unauthorized, NotFound {
        var isAuthorized = loggedIn.map(
            user->user.getRole() == Role.ADMIN
        )
        .orElse(false);

        if (!isAuthorized){
            throw new Unauthorized();
        }

        if (!dao.existsById(id)){
            throw new NotFound();
        }

        dao.deleteById(id);
    }

    public static class Unauthorized extends Exception{}
    public static class BookNotFound extends Exception{}
    public static class BookAlreadyHeld extends Exception{}
    public static class BookAlreadyReturned extends Exception{}
    public static class NotFound extends Exception{}

    boolean isBookHeld(int bookId){
        return dao.findByBook_BookId(bookId)
            .stream()
            .anyMatch(it->it.getDateActuallyReturned() == null);
    }
}