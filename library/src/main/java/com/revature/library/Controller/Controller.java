package com.revature.library.Controller;

import com.revature.library.Models.Book;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.User;
import com.revature.library.Service.BookLogService;
import com.revature.library.Service.BookService;
import com.revature.library.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
class Controller {

    private final UserService userService;

    private final BookService bookService;

    private final BookLogService bookLogService;

    @Autowired
    public Controller(UserService userService, BookService bookService, BookLogService bookLogService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookLogService = bookLogService;
    }

    
    static String USERNAME_KEY = "username";
    void setUser(User user, HttpSession session){
        session.setAttribute(USERNAME_KEY, user.getUsername());
    }

    Optional<User> getUser(HttpSession session){
        if (session.getAttribute(USERNAME_KEY) == null){
            return Optional.empty();
        }

        return userService.getByUsername(
            (String)session.getAttribute(USERNAME_KEY)
        );
    }

    //region user
    @PostMapping("users/login/{username}")
    public ResponseEntity<User> login(@PathVariable String username, @RequestBody String password, HttpSession session) {
        return userService.login(username, password)
            .map(user->{
                setUser(user, session);

                return ResponseEntity.ok(user);
            })
            .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("users/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
        } catch (UserService.NotAbsent | UserService.UsernameInvalid | UserService.EmailInvalid | UserService.PasswordInvalid e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("users/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        
        return ResponseEntity.ok().build();
    }

    @GetMapping("users")
    ResponseEntity<List<User>> getAllUser(HttpSession session) {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("users/{username}")
    ResponseEntity<User> getUser(@PathVariable String username, HttpSession session) {
        return userService
            .getByUsername(username)
            .map(
                it -> ResponseEntity.ok(it)
            )
            .orElse(
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            );
    }

    @PatchMapping("users/{username}")
    ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user, HttpSession session) {
        try {
            return ResponseEntity.ok(userService.editUser(username, user));
        } catch (UserService.NotFound | UserService.UsernameInvalid | UserService.EmailInvalid | UserService.PasswordInvalid e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("users/{username}")
    ResponseEntity<Void> deleteUser(@PathVariable String username, HttpSession session) {
        try {
            userService.deleteUser(username);

            return ResponseEntity.ok().build();
        } catch (UserService.IsHoldingBook e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UserService.NotFound e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    //endregion

    /*
     * get a single book
     * get all books
     * add books
     * edit book data
     * delete books(cannot delete books already issued)
     */
    //region books
    @GetMapping("books/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int bookId) {
        return bookService.getBookById(bookId).map(
                book -> ResponseEntity.ok(book)
            )
            .orElse(
                ResponseEntity.notFound().build()
            );
    }

    @GetMapping("books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("books")
    public ResponseEntity<Book> createNewBook(@RequestBody Book book, HttpSession session) {
        try {
            var newBook = bookService.createNewBook(book);

            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (BookService.TitleAndAuthorAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("books/{bookId}")
    public ResponseEntity<Book> editBook(@PathVariable int bookId, @RequestBody Book book, HttpSession session) {
        try {
            Book updatedBook = bookService.editBook(bookId, book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (BookService.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.noContent().build();
        } catch (BookService.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BookService.BookIsHeld e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    //endregion

    /*
     * booklogs:
     * get books from a userid
     * get users from a bookid
     * get all logs
     */
    //region booklog
    @PostMapping("bookLogs/{bookId}")
    ResponseEntity<BookLog> issueBook(@PathVariable int bookId, HttpSession session) {
        try {
            return ResponseEntity.ok(bookLogService.issueBook(bookId, getUser(session)));
        } catch (BookLogService.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (BookLogService.BookAlreadyHeld e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (BookLogService.BookNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("bookLogs/return/{bookLogId}")
    public ResponseEntity<Void> returnBook(@PathVariable int bookLogId, HttpSession session) {
        try {
            bookLogService.returnBook(bookLogId, getUser(session));

            return ResponseEntity.ok().build();
        }
        catch (BookLogService.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (BookLogService.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("bookLogs")
    ResponseEntity<List<BookLog>> getAllLogs(HttpSession session) {
        try {
            return ResponseEntity.ok(bookLogService.getAll(getUser(session)));
        } catch (BookLogService.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping("bookLogs/{logId}")
    ResponseEntity<BookLog> editLog(@PathVariable int logId, @RequestBody BookLog bookLog, HttpSession session) {
        try {
            return ResponseEntity.ok(bookLogService.edit(logId, bookLog, getUser(session)));
        }
        catch (BookLogService.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        catch (BookLogService.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("bookLogs/{logId}")
    ResponseEntity<Void> deleteLog(@PathVariable int logId, HttpSession session) {
        try {
            bookLogService.delete(logId, getUser(session));

            return ResponseEntity.ok(null);
        } catch (BookLogService.NotFound e) {
            return ResponseEntity.notFound().build();
        } catch (BookLogService.Unauthorized e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    //endregion
}