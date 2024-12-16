package com.revature.library.Service;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Models.BookLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLogService{

    private final BookLogDAO dao;

    @Autowired
    public BookLogService(BookLogDAO dao) {
        this.dao = dao;
    }

    public boolean isUserHoldingBook(String username){
        return dao.findByUsername(username)
            .stream()
            .anyMatch(it->it.getDateActuallyReturned() == null);
    }

    public List<BookLog> getAll(){
        return dao.findAll();
    }

    public List<BookLog> getAll(String username){
        return dao.findByUsername(username);
    }
}