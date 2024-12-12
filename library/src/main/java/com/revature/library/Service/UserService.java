package com.revature.library.Service;

import com.revature.library.DAO.UserDAO;
import com.revature.library.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    private final UserDAO dao;
    private final BookLogService bookLogService;

    @Autowired
    UserService(UserDAO dao, BookLogService bookLogService) {
        this.dao = dao;
        this.bookLogService = bookLogService;
    }

    private boolean validUsername(String username){
        if (getByUsername(username).isPresent()){
            return false;
        }

        throw new UnsupportedOperationException();
    }

    private boolean validPassword(String username){
        throw new UnsupportedOperationException();
    }

    private boolean validFirstOrLastName(String username){
        throw new UnsupportedOperationException();
    }

    private boolean validEmail(String username){
        throw new UnsupportedOperationException();
    }

    public Optional<User> getByUsername(String username){
        return dao.findById(username);
    }

    public List<User> getAll(){
        return dao.findAll();
    }

    public Optional<User> addUser(User user){
        if (!validUsername(user.getUsername())){
            return Optional.empty();
        }
        if (!validPassword(user.getUsername())){
            return Optional.empty();
        }
        if (!validFirstOrLastName(user.getFirstName())){
            return Optional.empty();
        }
        if (!validEmail(user.getEmail())){
            return Optional.empty();
        }
        //TODO check if DOB is valid

        return Optional.of(dao.save(user));
    }

    //For a user to be deleted, they must exist and not be holding a book
    public boolean removeUser(String username){
        if (bookLogService.isUserHoldingBook(username)){
            return false;
        }

        if (getByUsername(username).isEmpty()){
            return false;
        }

        dao.deleteById(username);
        return true;
    }
}