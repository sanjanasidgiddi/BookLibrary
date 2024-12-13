package com.revature.library.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.library.Models.BookLog;

import java.util.List;

@Repository
public interface BookLogDAO extends JpaRepository<BookLog,Integer> {
    List<BookLog> findByUsername(String username);
}
