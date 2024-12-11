package com.revature.library.Controller;

import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;
import com.revature.library.Service.BookLogService;
import com.revature.library.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class Controller{
    private final UserService userService;
    private final BookLogService bookLogService;

    private boolean isAdmin(){
        throw new UnsupportedOperationException();
    }

    //returns a different value if admin
    private String getLoggedInUserId(){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/users")
    ResponseEntity<List<User>> getAllUser(){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/users/{username}")
    ResponseEntity<User> getUser(@PathVariable String username){
        if (!isAdmin() && !getLoggedInUserId().equals(username)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return userService
            .getByUsername(username)
            .map(it-> ResponseEntity.ok(it))
            .orElse(
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            );
    }

    @DeleteMapping("/users/{username}")
    ResponseEntity<User> deleteUser(@PathVariable String username){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var user = userService.getByUsername(username);

        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!userService.removeUser(username)){
            //user is holding a book and cannot be deleted
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/logs")
    ResponseEntity<List<BookLog>> getAllLogs(){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll());
    }

    @GetMapping("/logs/{username}")
    ResponseEntity<List<BookLog>> getAllLogsByUsername(@PathVariable String username){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll(username));
    }
}