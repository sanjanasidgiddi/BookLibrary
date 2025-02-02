package com.revature.library.Service;

import com.revature.library.DAO.BookDAO;
import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Exceptions.BookExceptions;
import com.revature.library.Exceptions.BookLogExceptions;
import com.revature.library.Exceptions.Unauthorized;
import com.revature.library.Helper.Helper;
import com.revature.library.Models.Book;
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
        User user = Helper.requireLoggedIn(loggedIn);

        // if (isBookHeld(bookId)){
        //     throw new BookExceptions.IsHeld();
        // }

        Optional<Book> book = Optional.of(bookDAO
            .findById(bookId)
            .orElseThrow(
                ()-> new BookExceptions.NotFound()
            ));

        Calendar returnDate = Calendar.getInstance();
        returnDate.add(Calendar.DATE, 30);

        BookLog newLog = new BookLog();
        newLog.setUser(user);
        newLog.setBook(book.get());
        newLog.setDateIssued(new Date());
        newLog.setDateToBeReturned(returnDate.getTime());
        newLog.setDateActuallyReturned(null);

        return dao.save(newLog);
    }

    public BookLog returnBook(int bookLogId, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound, BookExceptions.AlreadyReturned {
        Optional<BookLog> log = Optional.of(dao
            .findById(bookLogId)
            .orElseThrow(
                ()->new BookLogExceptions.NotFound()
            ));

        if (log.get().getUser() == null){
            Helper.requireIsAdmin(loggedIn);
        }
        else {
            Helper.requireIsAdminOrOfUser(log.get().getUser().getUsername(), loggedIn);
        }

        if (log.get().getDateActuallyReturned() != null){
            throw new BookExceptions.AlreadyReturned();
        }

        log.get().setDateActuallyReturned(new Date());

        dao.save(log.get());

        return log.get();
    }

    public BookLog get(int id, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound {
        Optional<BookLog> log = Optional.of(dao
            .findById(id)
            .orElseThrow(
                ()->new BookLogExceptions.NotFound()
        ));

        if (log.get().getUser() == null){
            Helper.requireIsAdmin(loggedIn);
        }
        else {
            Helper.requireIsAdminOrOfUser(log.get().getUser().getUsername(), loggedIn);
        }

        return log.get();
    }

    public List<BookLog> getAll(Optional<User> loggedIn) throws Unauthorized {
        User user = Helper.requireLoggedIn(loggedIn);

        if (user.getRole() == Role.ADMIN){
            return dao.findAll();
        }
        return dao.findByUser_Username(user.getUsername());
    }

    public BookLog edit(int id, BookLog newBookLog, Optional<User> loggedIn) throws Unauthorized, BookLogExceptions.NotFound {
        Helper.requireIsAdmin(loggedIn);

        Optional<BookLog> logInTable = Optional.of(dao
            .findById(id)
            .orElseThrow(
                ()->new BookLogExceptions.NotFound()
            ));

        logInTable.get().setBook(newBookLog.getBook());
        logInTable.get().setUser(newBookLog.getUser());
        logInTable.get().setDateIssued(newBookLog.getDateIssued());
        logInTable.get().setDateToBeReturned(newBookLog.getDateToBeReturned());
        logInTable.get().setDateActuallyReturned(newBookLog.getDateActuallyReturned());

        dao.save(logInTable.get());

        return logInTable.get();
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