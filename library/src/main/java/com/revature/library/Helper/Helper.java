package com.revature.library.Helper;

import com.revature.library.Exceptions.Unauthorized;
import com.revature.library.Models.Role;
import com.revature.library.Models.User;

import java.util.Optional;

public class Helper {
    public static void requireIsAdmin(Optional<User> loggedIn) throws Unauthorized{
        var authorized = loggedIn.map(user->user.getRole()==Role.ADMIN).orElse(false);

        if (!authorized){
            throw new Unauthorized();
        }
    }

    public static void requireIsAdminOrOfUser(String username, Optional<User> loggedIn) throws Unauthorized{
        var authorized = loggedIn.map(user->user.getRole()==Role.ADMIN || user.getUsername().equals(username)).orElse(false);

        if (!authorized){
            throw new Unauthorized();
        }
    }
}
