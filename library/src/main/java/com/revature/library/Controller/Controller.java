package com.revature.library.Controller;

import com.revature.library.Models.Book;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;
import com.revature.library.Service.BookLogService;
import com.revature.library.Service.BookService;
import com.revature.library.Service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
class Controller{

    private final UserService userService;

    private final BookService bookService;

    private final BookLogService bookLogService;

    @Autowired
    public Controller(UserService userService, BookService bookService, BookLogService bookLogService){
        this.userService=userService;
        this.bookService=bookService;
        this.bookLogService = bookLogService;
    }

    private boolean isAdmin(HttpSession session){
        throw new UnsupportedOperationException();
    }

    //returns a different value if admin
    private String getLoggedInUserId(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> getAllUser(HttpSession session){
        if (!isAdmin(session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/users/{username}")
    ResponseEntity<User> getUser(@PathVariable String username, HttpSession session){
        if (!isAdmin(session) && !getLoggedInUserId().equals(username)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return userService
            .getByUsername(username)
            .map(it-> ResponseEntity.ok(it))
            .orElse(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            );
    }

    @PostMapping("/users/{username}")
    ResponseEntity<User> createUser(@RequestBody User user){
        try {
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } catch (UserService.NotAbsent | UserService.UsernameInvalid | UserService.EmailInvalid | UserService.PasswordInvalid e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/users/{username}")
    ResponseEntity<User> deleteUser(@PathVariable String username, HttpSession session){
        if (!isAdmin(session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        var user = userService.getByUsername(username);

        if (user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try{
            userService.deleteUser(username);

            return ResponseEntity.ok(user.get());
        } catch (UserService.IsHoldingBook e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserService.NotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /*
     * get a single book
     * get all books
     * add books
     * edit book data
     * delete books(cannot delete books already issued)
     */

    //get book by id
    @GetMapping("/books/{book_id}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId){
        return bookService.getBookById(bookId).map(
            book->ResponseEntity.ok(book)
        )
        .orElse(
            ResponseEntity.notFound().build()
        );
    }


     @GetMapping("books")
     public List<Book> getAllBooks(){
        return bookService.getAllBooks();
     }

     @PostMapping("books")
     public ResponseEntity<Book> createNewBook(@RequestBody Book book, HttpSession session){
         if (!isAdmin(session)){
             return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
         }

        try {
             var newBook = bookService.createNewBook(book);

             return new ResponseEntity<>(newBook, HttpStatus.CREATED);
         } catch (BookService.TitleAndAuthorAlreadyExists e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
         }
     }

     @PutMapping("books/{bookId}")
     public ResponseEntity<Book> editBook(@PathVariable("bookId") int bookId,@RequestBody Book book) throws NotFoundException{
        try{
            Book updatedBook=bookService.editBook(bookId, book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
        catch(BookService.NotFound e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
     }

     @DeleteMapping("/books/{bookId}")
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


     /*
      * booklogs:
            get books from a userid
            get users from a bookid
            get all logs


      */

      @GetMapping("/booklogs")
    ResponseEntity<List<BookLog>> getAllLogs(){
        if (!isAdmin(session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll());
    }

    @GetMapping("/booklogs/{username}")
    ResponseEntity<List<BookLog>> getAllLogsByUsername(@PathVariable String username){
        if (!isAdmin(session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll(username));
    }

    @GetMapping("/booklogs/{bookId}")
    ResponseEntity<List<BookLog>> getAllLogsByBookId(@PathVariable String BookId){
        if (!isAdmin(session)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll(BookId));
    }


}