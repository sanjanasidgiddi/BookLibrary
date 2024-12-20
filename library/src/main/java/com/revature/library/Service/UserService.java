package com.revature.library.Service;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.DAO.UserDAO;
import com.revature.library.Models.Role;
import com.revature.library.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserDAO dao;
    private final BookLogDAO bookLogDao;

    @Autowired
    UserService(UserDAO dao, BookLogDAO bookLogDAO) {
        this.dao = dao;
        this.bookLogDao = bookLogDAO;
    }

    public User register(User user) throws NotAbsent, UsernameInvalid, EmailInvalid, PasswordInvalid {
        //TODO check for admin username
        if (dao.existsById(user.getUsername())){
            throw new NotAbsent();
        }

        checkValidity(user);

        return dao.save(user);
    }

    public User login(String username, String password) throws Unauthorized{
        return dao.getUserByUsername(username)
            .filter(
                user->user.getPassword().equals(password)
            )
            .orElseThrow(()->new Unauthorized());
    }

    public User getByUsername(String username, Optional<User> loggedIn) throws Unauthorized, NotFound {
        if (!loggedIn.map(user->user.getRole() == Role.ADMIN || user.getUsername().equals(username)).orElse(false)){
            throw new Unauthorized();
        }

        return dao.findById(username).orElseThrow(()->new NotFound());
    }

    public List<User> getAll(Optional<User> loggedIn) throws Unauthorized {
        if (!loggedIn.map(user->user.getRole() == Role.ADMIN).orElse(false)){
            throw new Unauthorized();
        }

        return dao.findAll();
    }

    public User editUser(String username, User newUserInfo, Optional<User> loggedIn) throws NotFound, UsernameInvalid, EmailInvalid, PasswordInvalid, Unauthorized {
        if (loggedIn.map(user->user.getRole()==Role.ADMIN || user.getUsername().equals(username)).orElse(false)){
            throw new Unauthorized();
        }

        checkValidity(newUserInfo);

        var userInTable = dao.findById(username).orElseThrow(()->new NotFound());

        userInTable.setPassword(newUserInfo.getPassword());
        userInTable.setFirstName(newUserInfo.getFirstName());
        userInTable.setLastName(newUserInfo.getLastName());
        userInTable.setEmail(newUserInfo.getEmail());
        userInTable.setDob(newUserInfo.getDob());

        return dao.save(newUserInfo);
    }

    public void deleteUser(String username, Optional<User> loggedIn) throws NotFound, IsHoldingBook, Unauthorized {
        if (!loggedIn.map(user->user.getRole() == Role.ADMIN || user.getUsername().equals(username)).orElse(false)){
            throw new Unauthorized();
        }

        if (!dao.existsById(username)){
            throw new NotFound();
        }

        if (isUserHoldingBook(username)){
            throw new IsHoldingBook();
        }

        dao.deleteById(username);
    }

    public Optional<User> getByUsername(String username) {
        return dao.findById(username);
    }

    void checkValidity(User user) throws UsernameInvalid, PasswordInvalid, EmailInvalid {
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


    public static class Unauthorized extends Exception{}

    public static class NotFound extends Exception{};
    public static class NotAbsent extends Exception{};

    public static class UsernameInvalid extends Exception{};
    public static class PasswordInvalid extends Exception{};
    public static class EmailInvalid extends Exception{};

    public static class IsHoldingBook extends Exception{}

    boolean isUserHoldingBook(String username){
        return bookLogDao.findByUser_Username(username)
                .stream()
                .anyMatch(it->it.getDateActuallyReturned() == null);
    }
}