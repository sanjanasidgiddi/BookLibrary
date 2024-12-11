package com.revature.library.DAO;

import com.revature.library.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<String, UserModel> {

}