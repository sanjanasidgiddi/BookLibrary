package com.revature.library.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.revature.library.DAO.BookDAO;
import com.revature.library.Models.Book;

@Service
public class BookService {
    
    private final BookDAO bookDAO;

    @Autowired
    public BookService(BookDAO bookDAO){
        this.bookDAO=bookDAO;
    }

    //Get a book by Id
    public Optional<Book> getBookById(int BookId){
        return bookDAO.findById(BookId);
    }

    //Get all books
    public List<Book> getAllBooks(){
        return bookDAO.findAll();
    }

    //create a new book
    public Book createNewBook(Book newbook){
        /*Optional<Book> book=bookDAO.findById(newbook.getBookId());
        if(book.isPresent()){
            Book newBook=book.get();;
            return bookDAO.save(newBook);
        }
        else{
            throw new IllegalArgumentException("Book with ID " + newbook.getBookId() + " already exists.");
            
            //return null;
        }*/
        
        return bookDAO.save(newbook);
        
    }

    //edit book
    public Book editBook(int BookId, Book updatedBook) throws NotFoundException{
        Optional<Book> book=bookDAO.findById(BookId);

        if(book.isPresent()){
            Book existingBook=book.get();
            existingBook.setBookName(updatedBook.getBookName());
            existingBook.setBookGenre(updatedBook.getBookGenre());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setBookAgeLimit(updatedBook.getBookAgeLimit());
            return bookDAO.save(existingBook);
        }
        else{
            throw new NotFoundException();
        }
    }

    //delete book
    public void deleteBook(int BookId){
        //if (isBookBorrowed(BookId)) {
        //    throw new IllegalArgumentException("Book with ID " + BookId + " is currently borrowed and cannot be deleted.");
        //}
        //else{
            bookDAO.deleteById(BookId);
        //}
    }


    /*
     * get a single book
     * get all books
     * add books
     * edit book data
     * delete books(cannot delete books already issued)
     */
}
