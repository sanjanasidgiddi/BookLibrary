package com.revature.library;


import com.revature.library.Controller.Controller;
import com.revature.library.Models.Book;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.Role;
import com.revature.library.Models.User;
import com.revature.library.Service.BookLogService;
import com.revature.library.Service.BookService;
import com.revature.library.Service.UserService;
import com.revature.library.mocks.BookDAOMocker;
import com.revature.library.mocks.BookLogDAOMocker;
import com.revature.library.mocks.SessionMocker;
import com.revature.library.mocks.UserDAOMocker;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    Controller controller;
    
    HttpSession session;

    Book book1;
    Book book2;
    Book book3;

    BookLog log1;
    BookLog log2;

    User user1;
    User user2;
    User admin;

    @BeforeEach
    void beforeEach(){
        var userDao = new UserDAOMocker();
        var bookDao = new BookDAOMocker();
        var bookLogDao = new BookLogDAOMocker();

        controller = new Controller(
            new UserService(userDao, bookLogDao),
            new BookService(bookDao, bookLogDao),
            new BookLogService(bookLogDao, bookDao)
        );
        
        session = new SessionMocker();

        userDao.save(
            new User("admin", "password", "", "", "", "", new Date(), Role.ADMIN)
        );
        admin = userDao.findById("admin").get();

        userDao.save(
            new User("user1", "password", "", "", "", "", new Date(), Role.USER)
        );
        user1 = userDao.findById("user1").get();

        userDao.save(
            new User("user2", "password", "", "", "", "", new Date(), Role.USER)
        );
        user2 = userDao.findById("user2").get();

        bookDao.save(
            new Book(0, "Harry Potter", "JK Rowling", "Fantasy", 8, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1474169725i/15881.jpg")
        );
        book1 = bookDao.findById(1).get();

        bookDao.save(
            new Book(0, "Lord Of The Rings", "JJR Tolkien", "Fantasy", 12, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1566425108i/33.jpg")
        );
        book2 = bookDao.findById(2).get();

        bookDao.save(
            new Book(0, "Wheel Of Time", "Robert Jordan", "Fantasy", 12, "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1659905815i/228665.jpg")
        );
        book3 = bookDao.findById(3).get();

        bookLogDao.save(
            new BookLog(0, user1, book1, new Date(0, 0, 0), new Date(0, 0, 30), null)
        );
        log1 = bookLogDao.findById(1).get();

        bookLogDao.save(
            new BookLog(0, user2, book2, new Date(0, 0, 0), new Date(0, 0, 30), null)
        );
        log2 = bookLogDao.findById(2).get();
    }
    
    @Nested
    class Users{
        @Test
        void wrongPassword(){
            var loginInfo = Map.of(
                    "username", user1.getUsername(),
                    "password", "passwor"
            );

            assertUnauthorized(
                    controller.login(loginInfo, session)
            );
        }

        @Nested
        class C{
            @Test
            void registerNewUser(){
                var newUserInfo = new User("user3", "", "", "", "", "", new Date(), Role.USER);

                var user3 = assertCreated(
                    controller.register(newUserInfo)
                );

                assertSameInfo(newUserInfo, user3);
            }

            @Test
            void usernameAlreadyExists(){
                var newUserInfo = new User("user1", "", "", "", "", "", new Date(), Role.USER);

                assertConflicts(
                    controller.register(newUserInfo)
                );
            }
        }


        @Nested
        class R{
            @Test
            void usersCanSeeThemselves(){
                login(user1);

                assertEquals(
                        user1,
                        controller.getUser(user1.getUsername(), session).getBody()
                );
            }

            @Test
            void unloggedCantSeeOthers(){
                assertUnauthorized(controller.editUsers(user2.getUsername(), new User(), session));
            }

            @Test
            void usersCantSeeOthers(){
                login(user1);

                assertUnauthorized(controller.editUsers(user2.getUsername(), new User(), session));
            }

            @Test
            void adminsCanSeeOthers(){
                login(admin);

                assertOk(
                        controller.getUser(user2.getUsername(), session),
                        user2
                );
            }

            @Test
            void unloggedCantSeeAll(){
                assertUnauthorized(controller.getAllUsers(session));
            }

            @Test
            void usersCantSeeAll(){
                login(user1);

                assertUnauthorized(controller.getAllUsers(session));
            }

            @Test
            void adminsCanSeeAll(){
                login(admin);

                var list = assertOk(
                        controller.getAllUsers(session)
                );

                assertTrue(
                        list.contains(user1)
                );
                assertTrue(
                        list.contains(user2)
                );
                assertTrue(
                        list.contains(admin)
                );
            }
        }

        @Nested
        class U{
            @Test
            void unloggedCantEditOthers(){
                assertUnauthorized(controller.editUsers(user2.getUsername(), new User(), session));
            }

            @Test
            void userCantEditOthers(){
                login(user1);

                assertUnauthorized(controller.editUsers(user2.getUsername(), new User(), session));
            }

            @Test
            void adminsCanEditOthers(){
                login(admin);

                var newUserInfo = new User(
                    user1.getUsername(),
                    "newPassword",
                    "newEmail",
                    "newFirstName",
                    "newLastName",
                    "newPhoneNumber",
                    new Date(),
                    Role.USER
                );

                user1 = controller.editUsers(user1.getUsername(), newUserInfo, session).getBody();

                assertSameInfo(user1, newUserInfo);
            }

            @Test
            void usersCanEditThemselves(){
                login(user1);

                var newUserInfo = new User(
                    user1.getUsername(),
                    "newPassword",
                    "newEmail",
                    "newFirstName",
                    "newLastName",
                    "newPhoneNumber",
                    new Date(),
                    Role.USER
                );

                user1 = controller.editUsers(user1.getUsername(), newUserInfo, session).getBody();

                assertSameInfo(user1, newUserInfo);
            }
        }

        @Nested
        class D{
            @Test
            void unloggedCantDeleteOthers(){
                assertUnauthorized(
                    controller.deleteUser(user2.getUsername(), session)
                );
            }

            @Test
            void usersCantDeleteOthers(){
                login(user1);

                assertUnauthorized(
                    controller.deleteUser(user2.getUsername(), session)
                );
            }

            @Test
            void usersCanDeleteThemselves(){
                login(user1);

                assertConflicts(
                    controller.deleteUser(user1.getUsername(), session)
                );
            }

            @Test
            void adminsCantDeleteUsersHoldingBook(){
                login(admin);

                assertOk(
                    controller.getUser(user1.getUsername(), session)
                );

                assertConflicts(
                    controller.deleteUser(user1.getUsername(), session)
                );
            }

            @Test
            void adminsCanDeleteUsersNotHoldingBook(){
                login(user1);

                assertOk(
                    controller.returnBook(book1.getBookId(), session)
                );

                login(admin);

                assertOk(
                    controller.getUser(user1.getUsername(), session)
                );

                assertOk(
                    controller.deleteUser(user1.getUsername(), session)
                );

                assertNotFound(
                    controller.getUser(user1.getUsername(), session)
                );
            }
        }
    }

    @Nested
    class Books{
        @Nested
        class C{
            @Test
            void unloggedCantAddBooks(){
                assertUnauthorized(
                        controller.createNewBook(new Book(), session)
                );
            }

            @Test
            void usersCantAddBooks(){
                login(user1);

                assertUnauthorized(
                        controller.createNewBook(new Book(), session)
                );
            }

            @Test
            void adminsCanAddBooks(){
                login(admin);

                var newBookInfo = new Book(
                    0,
                    "newTitle",
                    "newAuthor",
                    "newGenre",
                    123,
                    "newImage"
                );

                var newBook = assertCreated(
                    controller.createNewBook(newBookInfo, session)
                );

                assertOk(
                    controller.getBook(newBook.getBookId()),
                    newBook
                );

                assertSameInfo(newBook, newBookInfo);
            }
        }

        @Nested
        class R{
            @Test
            void readExistingBook(){
                assertOk(
                        controller.getBook(book1.getBookId()),
                        book1
                );
            }

            @Test
            void readNonExistingBook(){
                assertNotFound(
                        controller.getBook(10)
                );
            }
        }

        @Nested
        class U{
            @Test
            void unloggedCantEditBooks(){
                assertUnauthorized(
                        controller.editBook(book1.getBookId(), new Book(), session)
                );
            }

            @Test
            void usersCantEditBooks(){
                login(user1);

                assertUnauthorized(
                        controller.editBook(book1.getBookId(), new Book(), session)
                );
            }

            @Test
            void adminsCanEditBooks(){
                login(admin);

                var newBookInfo = new Book(
                    0,
                    "newTitle",
                    "newAuthor",
                    "newGenre",
                    123,
                    "newImage"
                );

                book1 = assertOk(
                    controller.editBook(book1.getBookId(), newBookInfo, session)
                );

                assertSameInfo(book1, newBookInfo);
            }
        }

        @Nested
        class D{
            @Test
            void unloggedCantDeleteBooks(){
                assertUnauthorized(
                        controller.deleteBook(book1.getBookId(), session)
                );
            }

            @Test
            void usersCantDeleteBooks(){
                login(user1);

                assertUnauthorized(
                        controller.deleteBook(book1.getBookId(), session)
                );
            }

            @Test
            void adminsCantDeleteHeldBooks(){
                login(admin);

                assertOk(
                        controller.getBook(book1.getBookId())
                );

                assertConflicts(
                        controller.deleteBook(book1.getBookId(), session)
                );
            }

            @Test
            void adminsCanDeleteFreedBooks(){
                login(user1);

                assertOk(
                        controller.returnBook(log1.getBookLogId(), session)
                );

                login(admin);

                assertOk(
                        controller.getBook(book1.getBookId())
                );

                assertOk(
                        controller.deleteBook(book1.getBookId(), session)
                );

                assertNotFound(
                        controller.getBook(book1.getBookId())
                );
            }
        }
    }

    @Nested
    class Logs{
        @Test
        void usersCantBorrowBorrowedBook(){
            login(user2);

            assertConflicts(
                controller.borrowBook(book1.getBookId(), session)
            );
        }

        @Test
        void usersCanBorrowFreedBook(){
            login(user1);

            assertOk(
                controller.returnBook(log1.getBookLogId(), session)
            );

            login(user2);

            assertCreated(
                controller.borrowBook(book1.getBookId(), session)
            );
        }

        @Test
        void usersCanReturnOwnBooks(){
            login(user1);

            assertOk(
                controller.returnBook(log1.getBookLogId(), session)
            );
        }

        @Test
        void unloggedCantReturnOthersBooks(){
            assertUnauthorized(
                    controller.returnBook(log2.getBookLogId(), session)
            );
        }

        @Test
        void usersCantReturnOthersBooks(){
            login(user1);

            assertUnauthorized(
                    controller.returnBook(log2.getBookLogId(), session)
            );
        }

        @Test
        void adminsCanReturnOthersBooks(){
            login(admin);

            assertOk(
                    controller.returnBook(log2.getBookLogId(), session)
            );
        }

        @Test
        void usersCantDoubleReturnOwnBooks(){
            login(user1);

            assertOk(
                controller.returnBook(log1.getBookLogId(), session)
            );

            assertConflicts(
                controller.returnBook(log1.getBookLogId(), session)
            );
        }

        @Nested
        class R{
            @Test
            void unloggedCantReadLogs(){
                assertUnauthorized(
                        controller.getAllLogs(session)
                );
            }

            @Test
            void usersCanReadOwnLogs(){
                login(user1);

                var list = assertOk(
                        controller.getAllLogs(session)
                );

                assertEquals(
                        list,
                        List.of(log1)
                );
            }

            @Test
            void adminsCanReadAllLogs(){
                login(admin);

                var list = assertOk(
                    controller.getAllLogs(session)
                );

                assertEquals(
                    list,
                    List.of(log1, log2)
                );
            }
        }

        @Nested
        class U{
            @Test
            void unloggedCantEditLogs(){
                assertUnauthorized(
                        controller.editBook(book1.getBookId(), new Book(), session)
                );
            }

            @Test
            void usersCantEditLogs(){
                login(user1);

                assertUnauthorized(
                        controller.editBook(book1.getBookId(), new Book(), session)
                );
            }

            @Test
            void adminsCanEditLogs(){
                login(admin);

                var newLogInfo = new BookLog(
                    0,
                    user2,
                    book2,
                    new Date(),
                    new Date(),
                    new Date()
                );

                log1 = assertOk(
                    controller.editLog(log1.getBookLogId(), newLogInfo, session)
                );

                assertSameInfo(
                    newLogInfo, log1
                );
            }
        }

        @Nested
        class D{
            @Test
            void unloggedCantDeleteLogs(){
                assertUnauthorized(
                        controller.deleteBook(book1.getBookId(), session)
                );
            }

            @Test
            void usersCantDeleteLogs(){
                login(user1);

                assertUnauthorized(
                        controller.deleteBook(book1.getBookId(), session)
                );
            }

            @Test
            void adminsCanDeleteLogs(){
                login(admin);

                assertTrue(
                        controller.getAllLogs(session).getBody().contains(log1)
                );

                assertOk(
                        controller.deleteLog(log1.getBookLogId(), session)
                );

                assertFalse(
                        controller.getAllLogs(session).getBody().contains(log1)
                );
            }
        }
    }

    void login(User user){
        assertEquals(
            HttpStatus.UNAUTHORIZED,
            controller.getUser(user.getUsername(), session).getStatusCode()
        );

        var loginInfo = Map.of(
            "username", user.getUsername(),
            "password", user.getPassword()
        );

        assertOk(
            controller.login(loginInfo, session),
            user
        );

        assertEquals(
            user,
            controller.getUser(user.getUsername(), session).getBody()
        );
    }

    <T> T assertOk(ResponseEntity<T> r){
        assertEquals(
            HttpStatus.OK,
            r.getStatusCode()
        );

        return r.getBody();
    }

    <T> T assertOk(ResponseEntity<T> r, T item){
        assertEquals(
            HttpStatus.OK,
            r.getStatusCode()
        );

        assertEquals(
            item,
            r.getBody()
        );

        return r.getBody();
    }

    <T> T assertCreated(ResponseEntity<T> r){
        assertEquals(
            HttpStatus.CREATED,
            r.getStatusCode()
        );

        return r.getBody();
    }

    void assertUnauthorized(ResponseEntity r){
        assertEquals(
            HttpStatus.UNAUTHORIZED,
            r.getStatusCode()
        );

        assertFalse(
            r.hasBody()
        );
    }

    void assertConflicts(ResponseEntity r){
        assertEquals(
            HttpStatus.CONFLICT,
            r.getStatusCode()
        );

        assertFalse(
            r.hasBody()
        );
    }

    void assertNotFound(ResponseEntity r){
        assertEquals(
            HttpStatus.NOT_FOUND,
            r.getStatusCode()
        );

        assertFalse(
            r.hasBody()
        );
    }

    void assertSameInfo(User left, User right){
        assertTrue(
            left.getUsername().equals(right.getUsername()) &&
            left.getPassword().equals(right.getPassword()) &&
            left.getEmail().equals(right.getEmail()) &&
            left.getFirstName().equals(right.getFirstName()) &&
            left.getLastName().equals(right.getLastName()) &&
            left.getPhoneNumber().equals(right.getPhoneNumber()) &&
            left.getDob().equals(right.getDob()) &&
            left.getRole().equals(right.getRole())
        );
    }

    void assertSameInfo(Book left, Book right){
        assertTrue(
            left.getBookName().equals(right.getBookName()) &&
            left.getAuthor().equals(right.getAuthor()) &&
            left.getBookGenre().equals(right.getBookGenre()) &&
            left.getBookAgeLimit() == right.getBookAgeLimit() &&
            left.getImage().equals(right.getImage())
        );
    }

    void assertSameInfo(BookLog left, BookLog right){
        assertTrue(
            left.getBook().equals(right.getBook()) &&
            left.getUser().equals(right.getUser()) &&
            left.getDateIssued().equals(right.getDateIssued()) &&
            left.getDateToBeReturned().equals(right.getDateToBeReturned()) &&
            left.getDateActuallyReturned().equals(right.getDateActuallyReturned())
        );
    }
}
