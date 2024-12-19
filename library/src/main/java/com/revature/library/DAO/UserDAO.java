package com.revature.library.DAO;

import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

    Optional<User> getUserByUsername(String username);

}