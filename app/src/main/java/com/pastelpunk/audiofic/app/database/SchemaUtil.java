package com.pastelpunk.audiofic.app.database;

public class SchemaUtil {

    public final static String BOOK_TABLE = "CREATE TABLE BOOK (" +
            "id text primary key," +
            "name text," +
            "author text," +
            "description text" +
            ")";

    public final static String DROP_BOOK_TABLE = "DROP TABLE BOOK";

    public final static String CHAPTER_TABLE = "CREATE TABLE CHAPTER (" +
            "id text primary key, " +
            "bookId text, " +
            "chapterNumber int, "+
            "name text," +
            "description text," +
            "value text," +
            "FOREIGN KEY (bookId) REFERENCES BOOK (id)" +
            ")";

    public final static String DROP_CHAPTER_TABLE = "DROP TABLE CHAPTER";

    public final static String JOB_TABLE = "CREATE TABLE JOB (" +
            "id text primary key, " +
            ",path text " +
            ",type text "+
            ",status text" +
            ")";

    public final static String DROP_CHAPTER_JOB = "DROP TABLE JOB";
}
