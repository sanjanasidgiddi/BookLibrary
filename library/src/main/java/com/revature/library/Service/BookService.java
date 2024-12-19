package com.revature.library.Service;

import java.util.List;
import java.util.Optional;

import com.revature.library.DAO.BookLogDAO;
import com.revature.library.Models.Role;
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

    //create a new book
    public static class TitleAndAuthorAlreadyExists extends Exception{}
    public Book createNewBook(Book newbook) throws TitleAndAuthorAlreadyExists {
        var booksWithSameTitleAndAuthor = bookDAO.findByBookNameAndAuthor(newbook.getBookName(), newbook.getAuthor());

        if (!hasAuthor(newbook) && !booksWithSameTitleAndAuthor.isEmpty()){
            throw new TitleAndAuthorAlreadyExists();
        }

        return bookDAO.save(newbook);
    }

    //Get a book by Id
    public Optional<Book> getBookById(int BookId){
        return bookDAO.findById(BookId);
    }

    //Get all books
    public List<Book> getAllBooks(){
        return bookDAO.findAll();
    }


    //edit book
    public Book editBook(int bookId, Book updatedBook) throws NotFound {
        return bookDAO.findById(bookId)
            .map(existingBook-> {
                existingBook.setBookName(updatedBook.getBookName());
                existingBook.setBookGenre(updatedBook.getBookGenre());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setBookAgeLimit(updatedBook.getBookAgeLimit());
                return bookDAO.save(existingBook);
            })
            .orElseThrow(
                ()->new NotFound()
            );
    }

    //delete book
    public static class BookIsHeld extends Exception{}
    public void deleteBook(int bookId) throws NotFound, BookIsHeld {
        if (bookDAO.existsById(bookId)){
            throw new NotFound();
        }
        if (isBookHeld(bookId)){
            throw new BookIsHeld();
        }

        bookDAO.deleteById(bookId);
    }

    public static class NotFound extends Exception{}

    boolean isBookHeld(int bookId){
        return bookLogDAO.findByBook_BookId(bookId)
            .stream()
            .anyMatch(it->it.getDateActuallyReturned() == null);
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
