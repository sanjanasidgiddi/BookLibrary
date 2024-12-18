package com.revature.library.Service;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.DAO.UserDAO;
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

    public Optional<User> login(String username, String password){
        return dao.findById(username).flatMap(user->{
            if (user.getPassword().equals(password)){
                return Optional.of(user);
            }
            else {
                return Optional.empty();
            }
        });
    }

    public Optional<User> getByUsername(String username){
        return dao.findById(username);
    }

    public List<User> getAll(){
        return dao.findAll();
    }

    public User editUser(String username, User newUserInfo) throws NotFound, UsernameInvalid, EmailInvalid, PasswordInvalid {
        if (dao.existsById(newUserInfo.getUsername())){
            throw new NotFound();
        }

        checkValidity(newUserInfo);

        var userInTable = dao.findById(username).get();
        userInTable.setPassword(newUserInfo.getPassword());
        userInTable.setFirstName(newUserInfo.getFirstName());
        userInTable.setLastName(newUserInfo.getLastName());
        userInTable.setEmail(newUserInfo.getEmail());
        userInTable.setDob(newUserInfo.getDob());

        return dao.save(newUserInfo);
    }

    public static class IsHoldingBook extends Exception{}
    public void deleteUser(String username) throws NotFound, IsHoldingBook {
        if (!dao.existsById(username)){
            throw new NotFound();
        }

        if (isUserHoldingBook(username)){
            throw new IsHoldingBook();
        }

        dao.deleteById(username);
    }

    public static class NotFound extends Exception{};
    public static class NotAbsent extends Exception{};

    public static class UsernameInvalid extends Exception{};
    public static class PasswordInvalid extends Exception{};
    public static class EmailInvalid extends Exception{};
    void checkValidity(User user) throws UsernameInvalid, PasswordInvalid, EmailInvalid {
        if (!user.getUsername().matches("[a-zA-Z0-9_]{3,16}")){
            throw new UsernameInvalid();
        }
        if (!user.getPassword().matches(".{8,64}")){
            throw new PasswordInvalid();
        }
        if (!user.getEmail().matches("[^@]+@[^.]+\\.[^.]+")){
            throw new EmailInvalid();
        }
    }

    boolean isUserHoldingBook(String username){
        return bookLogDao.findByUser_Username(username)
                .stream()
                .anyMatch(it->it.getDateActuallyReturned() == null);
    }
}