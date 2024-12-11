package com.revature.library.Controller;

import com.revature.library.Models.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class Controller{
    private boolean isAdmin(){
        throw new UnsupportedOperationException();
    }

    //returns -1 if admin
    private int getLoggedInUserId(){
        throw new UnsupportedOperationException();
    }


    @PostMapping("/user/{id}")
    ResponseEntity<UserModel> getUser(@PathVariable int id){
        if (!isAdmin() && getLoggedInUserId() != id){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        throw new UnsupportedOperationException();
    }

    @PostMapping("/users")
    ResponseEntity<List<UserModel>> getAllUser(){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        throw new UnsupportedOperationException();
    }


    @DeleteMapping("/user/{id}")
    ResponseEntity<UserModel> deleteUser(@PathVariable int id){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        throw new UnsupportedOperationException();
    }
}