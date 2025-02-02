package com.revature.library.Service;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.DAO.UserDAO;
import com.revature.library.Exceptions.Unauthorized;
import com.revature.library.Exceptions.UserExceptions;
import com.revature.library.Helper.Helper;
import com.revature.library.Models.User;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserDAO dao;
    private final BookLogDAO bookLogDao;

    @Autowired
    public UserService(UserDAO dao, BookLogDAO bookLogDAO) {
        this.dao = dao;
        this.bookLogDao = bookLogDAO;
    }

    public User register(User user) throws UserExceptions.UsernameAlreadyTaken, UserExceptions.UsernameInvalid, UserExceptions.EmailInvalid, UserExceptions.PasswordInvalid {
        if (dao.existsById(user.getUsername())){
            throw new UserExceptions.UsernameAlreadyTaken();
        }

        checkValidity(user);

        return dao.save(user);
    }

    public User login(String username, String password) {
        return dao
            .findById(username)
            .filter(user->user.getPassword().equals(password))
            .orElse(null);
    }

    public User getByUsername(String username, Optional<User> loggedIn) throws Unauthorized, UserExceptions.NotFound {
        return dao
            .findById(username)
            .orElseThrow(
                ()->new UserExceptions.NotFound()
            );
    }

    public List<User> getAll(Optional<User> loggedIn) throws Unauthorized {
        return dao.findAll();
    }

    public User editUser(String username, User newUserInfo, Optional<User> loggedIn) throws UserExceptions.NotFound, UserExceptions.UsernameInvalid, UserExceptions.EmailInvalid, UserExceptions.PasswordInvalid, Unauthorized {
        checkValidity(newUserInfo);

        var userInTable = dao
            .findById(username)
            .orElseThrow(
                ()->new UserExceptions.NotFound()
            );

        userInTable.setPassword(newUserInfo.getPassword());
        userInTable.setFirstName(newUserInfo.getFirstName());
        userInTable.setLastName(newUserInfo.getLastName());
        userInTable.setEmail(newUserInfo.getEmail());
        userInTable.setPhoneNumber(newUserInfo.getPhoneNumber());
        userInTable.setDob(newUserInfo.getDob());

        return dao.save(newUserInfo);
    }

    public void deleteUser(String username, Optional<User> loggedIn) throws Unauthorized, UserExceptions.NotFound, UserExceptions.IsHoldingBook {
        if (!dao.existsById(username)){
            throw new UserExceptions.NotFound();
        }

        if (isUserHoldingBook(username)){
            throw new UserExceptions.IsHoldingBook();
        }

        for (var log: bookLogDao.findAll()){
            if (log.getUser() != null && log.getUser().getUsername().equals(username)){
                log.setUser(null);

                bookLogDao.save(log);
            }
        }

        dao.deleteById(username);
    }

    public Optional<User> getByUsername(String username) {
        return dao.findById(username);
    }

    void checkValidity(User user) throws UserExceptions.UsernameInvalid, UserExceptions.PasswordInvalid, UserExceptions.EmailInvalid {
//        if (!user.getUsername().matches("[a-zA-Z0-9_]{3,16}")){
//            throw new UsernameInvalid();
//        }
//        if (!user.getPassword().matches(".{8,64}")){
//            throw new PasswordInvalid();
//        }
//        if (!user.getEmail().matches("[^@]+@[^.]+\\.[^.]+")){
//            throw new EmailInvalid();
//        }
    }

    boolean isUserHoldingBook(String username){
        return bookLogDao.findByUser_Username(username)
                .stream()
                .anyMatch(it->it.getDateActuallyReturned() == null);
    }
}