package com.revature.library.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.library.Models.BookLog;

@Repository
public interface BookLogDAO extends JpaRepository<BookLog,Integer> {
    

}
/*
 * 
 */
