package com.revature.library.Service;


import com.revature.library.DAO.BookDAO;
import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Models.Book;
import com.revature.library.Models.BookLog;
import com.revature.library.Models.Role;
import com.revature.library.Models.User;
import com.revature.library.mocks.BookDAOMocker;
import com.revature.library.mocks.BookLogDAOMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookLogServiceTest {
    BookLogDAO bookLogDao;
    BookDAO bookDao;
    BookLogService bookLogService;

    Book book1;
    Book book2;
    Book book3;

    BookLog log1;
    BookLog log2;

    final User user1 = new User("user1", "sa", "", "", "", "", new Date(), Role.USER);
    final User user2 = new User("user2", "sa", "", "", "", "", new Date(), Role.USER);
    final User admin = new User("admin", "sa", "", "", "", "", new Date(), Role.ADMIN);

    @BeforeEach
    void beforeEach(){
        bookLogDao = new BookLogDAOMocker();
        bookDao = new BookDAOMocker();
        bookLogService = new BookLogService(bookLogDao, bookDao);

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
            new BookLog(user1, book1, 0, new Date(0, 0, 0), new Date(0, 0, 30), null)
        );
        log1 = bookLogDao.findById(1).get();

        bookLogDao.save(
            new BookLog(user2, book2, 0, new Date(0, 0, 0), new Date(0, 0, 30), null)
        );
        log2 = bookLogDao.findById(1).get();
    }

    @Nested
    class Permissions{
        @Test
        void mustBeLoggedIn(){
            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.issueBook(3, Optional.empty())
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.returnBook(1, Optional.empty())
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.get(1, Optional.empty())
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.getAll(Optional.empty())
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.edit(1, new BookLog(), Optional.empty())
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.delete(1, Optional.empty())
            );

        }

        @Test
        void wrongUser(){
            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.returnBook(1, Optional.of(user2))
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.get(1, Optional.of(user2))
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.edit(1, new BookLog(), Optional.of(user2))
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.delete(1, Optional.of(user2))
            );
        }

        @Test
        void userCantEditTheirOwnLogs(){
            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.edit(1, new BookLog(), Optional.of(user1))
            );

            assertThrows(
                BookLogService.Unauthorized.class,
                ()->bookLogService.delete(1, Optional.of(user1))
            );
        }

        @Test
        void adminsCanEditLogs(){
            try {
                assertInstanceOf(
                    BookLog.class,
                    bookLogService.edit(1, new BookLog(), Optional.of(admin))
                );

                bookLogService.delete(1, Optional.of(admin));
            } catch (BookLogService.Unauthorized | BookLogService.NotFound e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        void userCanGetOwnLogs(){
            try {
                assertInstanceOf(
                    BookLog.class,
                    bookLogService.get(1, Optional.of(user1))
                );
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        void userCanIssue(){
            try {
                assertInstanceOf(
                    BookLog.class,
                    bookLogService.issueBook(3, Optional.of(user1))
                );
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        void userCanReturn(){
            try {
                assertInstanceOf(
                    BookLog.class,
                    bookLogService.returnBook(1, Optional.of(user1))
                );
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
