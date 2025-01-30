package com.revature.library.Helper;

import com.revature.library.Exceptions.Unauthorized;
import com.revature.library.Models.Role;
import com.revature.library.Models.User;

import java.util.Optional;

public final class Helper {
    private Helper(){}

    public static User requireIsAdmin(Optional<User> loggedIn) throws Unauthorized{
        System.out.println("Logged in USER is -------------> " + loggedIn.toString());
        return loggedIn
            .filter(
                user->user.getRole()==Role.ADMIN
            )
            .orElseThrow(
                ()->new Unauthorized()
            );
    }

    public static User requireIsAdminOrOfUser(String username, Optional<User> loggedIn) throws Unauthorized{
        System.out.println("Logged in USER is -------------> " + loggedIn.toString());
        return loggedIn
            .filter(
                user->user.getRole()==Role.ADMIN || user.getUsername().equals(username)
            )
            .orElseThrow(
                ()->new Unauthorized()
            );
    }

    public static User requireLoggedIn(Optional<User> loggedIn) throws Unauthorized {
        System.out.println("Logged in USER is -------------> " + loggedIn.toString());
        return loggedIn
            .orElseThrow(
                ()->new Unauthorized()
            );
    }
}
