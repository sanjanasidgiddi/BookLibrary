package com.revature.library.DAO;

import com.revature.library.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, String> {

}