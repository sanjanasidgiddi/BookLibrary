package com.revature.library.DAO;

import com.revature.library.Models.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.library.Models.Book;

import java.util.List;

@Repository
public interface BookDAO extends JpaRepository<Book,Integer> {
    List<Book> findByBookNameAndAuthor(String bookName, String author);
}

