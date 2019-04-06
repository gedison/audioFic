package com.pastelpunk.audiofic.app.database.book;

import android.database.Cursor;

import com.pastelpunk.audiofic.core.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {

    public static List<Book> mapBooks(Cursor cursor){
        List<Book> books = new ArrayList<>();
        while(cursor.moveToNext()) {
            books.add(BookMapper.mapBook(cursor));
        }
        return books;
    }

    public static Book mapBook(Cursor cursor){
        Book ret = new Book();
        String id = cursor.getString(0);
        String name = cursor.getString(1);
        String author = cursor.getString(2);
        String description = cursor.getString(3);

        ret.setId(id);
        ret.setName(name);
        ret.setAuthor(author);
        ret.setDescription(description);
        return ret;
    }
}
