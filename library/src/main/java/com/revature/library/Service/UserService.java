package com.revature.library.Service;

import com.revature.library.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserService{
    private final UserDAO dao;

    @Autowired
    UserService(UserDAO dao) {
        this.dao = dao;
    }
}