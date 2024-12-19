package com.revature.library.Controller;

import com.revature.library.Models.Book;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.Role;
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
import java.util.Objects;
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

    boolean isAdmin(HttpSession session) {
        //TODO there is probably a better way
        //String role = (String) session.getAttribute("role");
        //System.out.println("Session role: " + role);  // Debugging
        return "ADMIN".equals(session.getAttribute("role"));
    }

    //returns a different value if admin
    String getLoggedInUsername(HttpSession session) {
        var username = session.getAttribute("username");
        if (username == null){
            return "";
        }
        return (String) username;
    }

    //region user
//     @PostMapping("users/login/{username}")
//     public ResponseEntity<User> login(@PathVariable String username, @RequestBody String password, HttpSession session) {
//     System.out.println("Password from request: " + username + password); 

//     if (!isAdmin(session)) {
//         return ResponseEntity.ok(new User()); 
//     }

//     Optional<User> optionalUser = userService.login(username, password);

//     if (optionalUser.isEmpty()) {
//         return ResponseEntity.badRequest().build();
//     }

//     User user = optionalUser.get();
//     session.setAttribute("username", user.getUsername());
//     session.setAttribute("role", user.getRole().name());

//     return ResponseEntity.ok(user); 
//     }
    

    @PostMapping("users/login") // http://localhost:8080/users/login
    public ResponseEntity<User> loginHandler(@RequestBody User user, HttpSession session){
        // Now I want to validate that the user has provided the correct credentials

        User returnedUser = userService.login(user.getUsername(), user.getPassword());

        if (returnedUser == null){
            // This means the user had the wrong credentials or we couldn't find the user with the specific username
            return ResponseEntity.badRequest().build();
            // return new ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        // We'll store some information inside of the session to hold it for later
        session.setAttribute("username", returnedUser.getUsername());
        session.setAttribute("userId", returnedUser.getUsername());
        session.setAttribute("role", returnedUser.getRole().name());


        // OTHERWISE
        return ResponseEntity.ok(returnedUser);
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
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("users/{username}")
    ResponseEntity<User> getUser(@PathVariable String username, HttpSession session) {
        if (!isAdmin(session) && !getLoggedInUsername(session).equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return userService
            .getByUsername(username)
            .map(it -> ResponseEntity.ok(it))
            .orElse(
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            );
    }

    @PatchMapping("users/{username}")
    ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User user, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            return ResponseEntity.ok(userService.editUser(username, user));
        } catch (UserService.NotFound | UserService.UsernameInvalid | UserService.EmailInvalid | UserService.PasswordInvalid e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("users/{username}")
    ResponseEntity<Void> deleteUser(@PathVariable String username, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

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
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            var newBook = bookService.createNewBook(book);

            return new ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (BookService.TitleAndAuthorAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("books/{bookId}")
    public ResponseEntity<Book> editBook(@PathVariable int bookId, @RequestBody Book book, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

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
    @PostMapping("bookLogs")
    ResponseEntity<BookLog> createLog(@RequestBody BookLog log, HttpSession session) {
        if (!isAdmin(session) || !getLoggedInUsername(session).equals(log.getUser().getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            return ResponseEntity.ok(bookLogService.issue(log));
        } catch (BookLogService.BookAlreadyHeld | BookLogService.InvalidReturnDate e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("bookLogs")
    ResponseEntity<List<BookLog>> getAllLogs(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll());
    }

    @GetMapping("bookLogs/{username}")
    ResponseEntity<List<BookLog>> getAllLogsByUsername(@PathVariable String username, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll(username));
    }

    @GetMapping("bookLogs/{bookId}")
    ResponseEntity<List<BookLog>> getAllLogsByBookId(@PathVariable String bookId, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(bookLogService.getAll(bookId));
    }

    @PatchMapping("bookLogs/{logId}")
    ResponseEntity<Void> editLog(@PathVariable int logId, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            bookLogService.delete(logId);

            return ResponseEntity.ok(null);
        } catch (BookLogService.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("bookLogs/{logId}")
    ResponseEntity<Void> deleteLog(@PathVariable int logId, HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            bookLogService.delete(logId);

            return ResponseEntity.ok(null);
        } catch (BookLogService.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("books/{username}")
    public ResponseEntity<List<BookLog>> getAllBookByusername(@PathVariable String username) {
        return ResponseEntity.ok(bookLogService.getAll(username));
    }

    //endregion
}