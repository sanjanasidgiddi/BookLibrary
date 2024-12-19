package com.revature.library.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookLogDAO extends JpaRepository<BookLog,Integer> {
    List<BookLog> findByUser_Username(String username);

    List<BookLog> findByBook_BookId(int id);

//    Optional<User> findBookByUser_Username(String username);
}
