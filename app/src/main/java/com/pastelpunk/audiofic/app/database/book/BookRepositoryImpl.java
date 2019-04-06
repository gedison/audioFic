package com.pastelpunk.audiofic.app.database.book;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pastelpunk.audiofic.app.database.AudioFicDBHelper;
import com.pastelpunk.audiofic.core.model.Book;

import java.util.List;

import javax.inject.Inject;

public class BookRepositoryImpl implements BookRepository {
    private final static String CREATE = "INSERT INTO BOOK (id, name, author, description) VALUES (?,?,?,?)";
    private final static String UPDATE = "UPDATE BOOK SET name = ?, author = ?, description = ? WHERE ID = ?";
    private final static String DELETE = "DELETE FROM BOOK WHERE ID = ?";
    private final static String DELETE_EMPTY = "DELETE FROM BOOK WHERE name is null OR name = ''";
    private final static String GET = "SELECT id, name, author, description FROM BOOK WHERE ID = ?";
    private final static String GET_ALL = "SELECT id, name, author, description FROM BOOK";

    private SQLiteDatabase dataSource;

    @Inject
    public BookRepositoryImpl(SQLiteOpenHelper dbHelper){
        this.dataSource = dbHelper.getWritableDatabase();
    }

    @Override
    public Book createBook(Book toCreate){
        Object[] values = new Object[]{toCreate.getId(), toCreate.getName(),
                toCreate.getAuthor(), toCreate.getDescription()};
        dataSource.execSQL(CREATE, values);
        return toCreate;
    }

    @Override
    public Book updateBook(String id, Book toUpdate){
        Object[] values = new Object[]{toUpdate.getName(), toUpdate.getAuthor(),
                toUpdate.getDescription(), toUpdate.getId()};
        dataSource.execSQL(UPDATE, values);
        return getBook(toUpdate.getId());

    }

    @Override
    public void deleteBook(String id){
        Object[] values = new Object[]{id};
        dataSource.execSQL(DELETE, values);
    }

    public void deleteEmptyBooks(){
        dataSource.execSQL(DELETE_EMPTY);
    }

    @Override
    public Book getBook(String id){
        String[] values = new String[]{id};
        try(Cursor cursor = dataSource.rawQuery(GET, values)){
            List<Book> books = BookMapper.mapBooks(cursor);
            return (books.isEmpty()) ? null : books.get(0);
        }
    }

    @Override
    public List<Book> getAllBooks(){
        try(Cursor cursor = dataSource.rawQuery(GET_ALL, null)){
            return BookMapper.mapBooks(cursor);
        }
    }
}
