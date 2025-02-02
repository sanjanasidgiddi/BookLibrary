package com.revature.library.Service;

import java.util.List;
import java.util.Optional;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Exceptions.BookExceptions;
import com.revature.library.Exceptions.Unauthorized;
import com.revature.library.Helper.Helper;
import com.revature.library.Models.Role;
import org.hibernate.TransientObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.library.DAO.BookDAO;
import com.revature.library.Models.Book;
import com.revature.library.Models.User;

@Service
public class BookService {
    private final BookDAO bookDAO;
    private final BookLogDAO bookLogDAO;

    @Autowired
    public BookService(BookDAO bookDAO, BookLogDAO bookLogDAO){
        this.bookDAO=bookDAO;
        this.bookLogDAO = bookLogDAO;
    }

    public Book createNewBook(Book newbook, Optional<User> loggedIn) throws Unauthorized, BookExceptions.TitleAndAuthorAlreadyExists {
        checkValidity(newbook);

        return bookDAO.save(newbook);
    }

    public Book getBookById(int BookId) throws BookExceptions.NotFound {
        return bookDAO
            .findById(BookId)
            .orElseThrow(
                ()->new BookExceptions.NotFound()
            );
    }

    public List<Book> getAllBooks(){
        return bookDAO.findAll();
    }

    public Book editBook(int bookId, Book updatedBook, Optional<User> loggedIn) throws Unauthorized, BookExceptions.NotFound {
        return bookDAO.findById(bookId)
            .map(existingBook-> {
                existingBook.setBookName(updatedBook.getBookName());
                existingBook.setBookGenre(updatedBook.getBookGenre());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setBookAgeLimit(updatedBook.getBookAgeLimit());
                existingBook.setImage(updatedBook.getImage());
                return bookDAO.save(existingBook);
            })
            .orElseThrow(
                ()->new BookExceptions.NotFound()
            );
    }

    public void deleteBook(int bookId, Optional<User> loggedIn) throws Unauthorized, BookExceptions.NotFound, BookExceptions.IsHeld {

        for (var log: bookLogDAO.findAll()){
            if (log.getBook() != null && log.getBook().getBookId() == bookId){
                log.setBook(null);

                bookLogDAO.save(log);
            }
        }

        bookDAO.deleteById(bookId);
    }

    boolean isBookHeld(int bookId){
        return bookLogDAO.findByBook_BookId(bookId)
            .stream()
            .anyMatch(it->it.getDateActuallyReturned() == null);
    }

    void checkValidity(Book book) throws BookExceptions.TitleAndAuthorAlreadyExists {
//        var booksWithSameTitleAndAuthor = bookDAO.findByBookNameAndAuthor(newbook.getBookName(), newbook.getAuthor());
//        if (hasAuthor(book) && !booksWithSameTitleAndAuthor.isEmpty()){
//            throw new BookExceptions.TitleAndAuthorAlreadyExists();
//        }
    }

    boolean hasAuthor(Book book){
        return !book.getAuthor().isBlank();
    }

    /*
     * get a single book
     * get all books
     * add books
     * edit book data
     * delete books(cannot delete books already issued)
     */
}
