package com.revature.library.mocks;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Models.BookLog;

import java.util.List;
import java.util.stream.Collectors;

public class BookLogDAOMocker extends DaoMocker<BookLog, Integer> implements BookLogDAO {
    @Override
    Integer getId(BookLog entry) {
        return entry.getBookLogId();
    }

    @Override
    BookLog addId(BookLog entry, int index) {
        return new BookLog(
                index, entry.getUser(),
            entry.getBook(),
                entry.getDateIssued(),
            entry.getDateToBeReturned(),
            entry.getDateActuallyReturned()
        );
    }

    @Override
    public List<BookLog> findByUser_Username(String username) {
        return this
            .findAll()
            .stream()
            .filter(
                x -> x.getUser().getUsername().equals(username)
            )
            .collect(Collectors.toList());
    }

    @Override
    public List<BookLog> findByBook_BookId(int id) {
        return this
            .findAll()
            .stream()
            .filter(
                x -> x.getBook().getBookId() == id
            )
            .collect(Collectors.toList());
    }
}
