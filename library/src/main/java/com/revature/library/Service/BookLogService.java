package com.revature.library.Service;

import com.revature.library.DAO.BookDAO;
import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Exceptions.BookExceptions;
import com.revature.library.Exceptions.BookLogExceptions;
import com.revature.library.Exceptions.Unauthorized;
import com.revature.library.Helper.Helper;
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

    public BookLog issueBook(int bookId, Optional<User> loggedIn) throws Unauthorized, BookExceptions.IsHeld, BookExceptions.NotFound {
        var user = Helper.requireLoggedIn(loggedIn);

        if (isBookHeld(bookId)){
            throw new BookExceptions.IsHeld();
        }

        var book = bookDAO
            .findById(bookId)
            .orElseThrow(
                ()-> new BookExceptions.NotFound()
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

    public BookLog returnBook(int bookLogId, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound, BookExceptions.AlreadyReturned {
        var log = dao
            .findById(bookLogId)
            .orElseThrow(
                ()->new BookLogExceptions.NotFound()
            );

        Helper.requireIsAdminOrOfUser(log.getUser().getUsername(), loggedIn);

        if (log.getDateActuallyReturned() != null){
            throw new BookExceptions.AlreadyReturned();
        }

        log.setDateActuallyReturned(new Date());

        dao.save(log);

        return log;
    }

    public BookLog get(int id, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound {
        var log = dao
            .findById(id)
            .orElseThrow(
                ()->new BookLogExceptions.NotFound()
            );

        Helper.requireIsAdminOrOfUser(log.getUser().getUsername(), loggedIn);

        return log;
    }

    public List<BookLog> getAll(Optional<User> loggedIn) throws Unauthorized {
        var user = Helper.requireLoggedIn(loggedIn);

        if (user == null || user.getRole() == Role.ADMIN){
            return dao.findAll();
        }
        return dao.findByUser_Username(user.getUsername());
    }

    public BookLog edit(int id, BookLog newBookLog, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound {
        Helper.requireIsAdmin(loggedIn);

        var logInTable = dao
            .findById(id)
            .orElseThrow(
                ()->new BookLogExceptions.NotFound()
            );

        logInTable.setBook(newBookLog.getBook());
        logInTable.setUser(newBookLog.getUser());
        logInTable.setDateIssued(newBookLog.getDateIssued());
        logInTable.setDateToBeReturned(newBookLog.getDateToBeReturned());
        logInTable.setDateActuallyReturned(newBookLog.getDateActuallyReturned());

        dao.save(logInTable);

        return logInTable;
    }

    public void delete(int id, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound {
        Helper.requireIsAdmin(loggedIn);

        if (!dao.existsById(id)){
            throw new BookLogExceptions.NotFound();
        }

        dao.deleteById(id);
    }

    boolean isBookHeld(int bookId){
        return dao.findByBook_BookId(bookId)
            .stream()
            .anyMatch(it->it.getDateActuallyReturned() == null);
    }
}