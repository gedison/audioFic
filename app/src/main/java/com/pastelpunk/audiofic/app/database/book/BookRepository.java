package com.pastelpunk.audiofic.app.database.book;

import com.pastelpunk.audiofic.core.model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookRepository {
    Book createBook(Book toCreate);
    Book updateBook(String id, Book toUpdate);
    void deleteBook(String id);
    void deleteEmptyBooks();
    Book getBook(String id);
    List<Book> getAllBooks();

}
