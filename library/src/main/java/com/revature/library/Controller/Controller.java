package com.revature.library.Controller;

import com.revature.library.Models.Book;
import com.revature.library.Models.User;
import com.revature.library.Service.BookService;
import com.revature.library.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
class Controller{

    private final UserService userService;

    private final BookService bookService;

    @Autowired
    public Controller(UserService userService, BookService bookService){
        this.userService=userService;
        this.bookService=bookService;
    }

    private boolean isAdmin(){
        throw new UnsupportedOperationException();
    }

    //returns -1 if admin
    private int getLoggedInUserId(){
        throw new UnsupportedOperationException();
    }


    @PostMapping("user/{id}")
    ResponseEntity<User> getUser(@PathVariable int id){
        if (!isAdmin() && getLoggedInUserId() != id){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        throw new UnsupportedOperationException();
    }

    @PostMapping("user")
    ResponseEntity<List<User>> getAllUser(){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        throw new UnsupportedOperationException();
    }


    @DeleteMapping("user/{id}")
    ResponseEntity<User> deleteUser(@PathVariable int id){
        if (!isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        throw new UnsupportedOperationException();
    }

    /*
     * get a single book
     * get all books
     * add books
     * edit book data
     * delete books(cannot delete books already issued)
     */

     @GetMapping
     public List<Book> getAllBooks(){
        return bookService.getAllBooks();
     }

     @PostMapping("book/{book_id}")
     public ResponseEntity<Book> createNewBook(@RequestBody Book book){
        Book newBook=bookService.createNewBook(book);
        /*if(newBook==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        }*/

        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
     }

     @PutMapping("book/{book_id}")
     public ResponseEntity<Book> editBook(@PathVariable int bookId,@RequestBody Book book) throws NotFoundException{
        try{
            Book updatedBook=bookService.editBook(bookId, book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        catch(NotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
     }

     @DeleteMapping("book/{book_id}")
     public ResponseEntity<Void> deleteBook(@PathVariable int bookId){
        try{
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        }
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }



}