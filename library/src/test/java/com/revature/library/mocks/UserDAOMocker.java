package com.revature.library.mocks;

import com.revature.library.DAO.BookDAO;
import com.revature.library.DAO.UserDAO;
import com.revature.library.Models.Book;
import com.revature.library.Models.User;
import com.revature.library.mocks.DaoMocker;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDAOMocker extends DaoMocker<User, String> implements UserDAO {
    @Override
    String getId(User entry) {
        return entry.getUsername();
    }

    @Override
    User addId(User entry, int index) {
        return entry;
    }
}
