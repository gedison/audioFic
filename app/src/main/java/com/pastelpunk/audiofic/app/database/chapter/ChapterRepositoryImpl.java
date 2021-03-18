package com.pastelpunk.audiofic.app.database.chapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pastelpunk.audiofic.core.model.Chapter;

import java.util.List;

import javax.inject.Inject;

public class ChapterRepositoryImpl implements ChapterRepository {

    private final static String CREATE = "INSERT INTO CHAPTER (id, bookId, chapterNumber, name, description, value) VALUES (?,?,?,?,?,?)";
    private final static String UPDATE = "UPDATE CHAPTER SET name = ?, chapterNumber = ?, description = ?, value = ? WHERE BOOKID = ? AND ID = ?";
    private final static String DELETE = "DELETE FROM CHAPTER WHERE BOOKID = ? AND ID = ?";
    private final static String GET = "SELECT id, bookId, chapterNumber, name, description, value FROM CHAPTER WHERE BOOKID = ? AND ID = ?";
    private final static String GET_BY_NUMBER = "SELECT id, bookId, chapterNumber, name, description, value FROM CHAPTER WHERE BOOKID = ? AND chapterNumber = ?";
    private final static String GET_ALL = "SELECT id, bookId, chapterNumber, name, description, value FROM CHAPTER WHERE BOOKID = ?";
    private final static String GET_ALL_TITLES = "SELECT id, bookId, chapterNumber, name, description, 'null' FROM CHAPTER WHERE BOOKID = ?";

    private SQLiteDatabase dataSource;

    @Inject
    public ChapterRepositoryImpl(SQLiteOpenHelper dbHelper){
        this.dataSource =  dbHelper.getWritableDatabase();
    }

    @Override
    public Chapter createChapter(String bookId, Chapter toCreate) {
        Object[] values = new Object[]{toCreate.getId(), bookId, toCreate.getChapterNumber(), toCreate.getName(),
                toCreate.getDescription(), toCreate.getText()};
        dataSource.execSQL(CREATE, values);
        return toCreate;
    }

    @Override
    public Chapter updateChapter(String bookId, String chapterId, Chapter toUpdate) {
        Object[] values = new Object[]{toUpdate.getName(), toUpdate.getChapterNumber(), toUpdate.getDescription(),
                toUpdate.getText(), bookId, toUpdate.getId()};
        dataSource.execSQL(UPDATE, values);
        return getChapter(bookId, toUpdate.getId());
    }

    @Override
    public void deleteChapter(String bookId, String chapterId) {
        Object[] values = new Object[]{bookId, chapterId};
        dataSource.execSQL(DELETE, values);
    }

    @Override
    public List<Chapter> getAllChapters(String bookId) {
        String[] values = new String[]{bookId};
        try(Cursor cursor = dataSource.rawQuery(GET_ALL, values)){
            return ChapterMapper.mapChapters(cursor);
        }
    }

    @Override
    public List<Chapter> getChapterTitles(String bookId) {
        String[] values = new String[]{bookId};
        try(Cursor cursor = dataSource.rawQuery(GET_ALL_TITLES, values)){
            return ChapterMapper.mapChapters(cursor);
        }
    }

    @Override
    public Chapter getChapter(String bookId, String chapterId) {
        String[] values = new String[]{bookId, chapterId};
        try(Cursor cursor = dataSource.rawQuery(GET, values)){
            List<Chapter> chapters = ChapterMapper.mapChapters(cursor);
            return (chapters.isEmpty()) ? null : chapters.get(0);

        }
    }

    @Override
    public Chapter getIthChapter(String bookId, int chapterNumber) {
        String[] values = new String[]{bookId, Integer.toString(chapterNumber)};
        try(Cursor cursor = dataSource.rawQuery(GET_BY_NUMBER, values)){
            List<Chapter> chapters = ChapterMapper.mapChapters(cursor);
            return (chapters.isEmpty()) ? null : chapters.get(0);
        }
    }
}
