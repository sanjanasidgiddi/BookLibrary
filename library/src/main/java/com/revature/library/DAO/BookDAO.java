package com.revature.library.DAO;

import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.library.Models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookDAO extends JpaRepository<Book,Integer> {
    List<Book> findByBookNameAndAuthor(String bookName, String author);

    
}

