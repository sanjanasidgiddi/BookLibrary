package com.revature.library.mocks;

import com.revature.library.DAO.BookDAO;
import com.revature.library.Models.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookDAOMocker extends DaoMocker<Book, Integer> implements BookDAO {
    @Override
    Integer getId(Book entry) {
        return entry.getBookId();
    }

    @Override
    Book addId(Book entry, int index) {
        return new Book(
            index,
            entry.getBookName(),
            entry.getAuthor(),
            entry.getBookGenre(),
            entry.getBookAgeLimit(),
            entry.getImage()
        );
    }

    @Override
    public List<Book> findByBookNameAndAuthor(String bookName, String author) {
        return findAll()
            .stream()
            .filter(
                x->x.getBookName().equals(bookName) && x.getAuthor().equals(author)
            )
            .collect(Collectors.toList());
    }
}
