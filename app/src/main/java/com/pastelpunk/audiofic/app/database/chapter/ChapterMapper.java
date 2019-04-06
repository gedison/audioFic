package com.pastelpunk.audiofic.app.database.chapter;

import android.database.Cursor;

import com.pastelpunk.audiofic.app.database.book.BookMapper;
import com.pastelpunk.audiofic.core.model.Book;
import com.pastelpunk.audiofic.core.model.Chapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChapterMapper {

    public static List<Chapter> mapChapters(Cursor cursor){
        List<Chapter> chapters = new ArrayList<>();
        while(cursor.moveToNext()) {
            chapters.add(ChapterMapper.mapChapter(cursor));
        }
        return chapters;
    }

    public static Chapter mapChapter(Cursor cursor){
        Chapter ret = new Chapter();

        String id = cursor.getString(0);
        String bookId = cursor.getString(1);
        Integer chapterNumber = cursor.getInt(2);
        String name = cursor.getString(3);
        String description = cursor.getString(4);
        String value = cursor.getString(5);

        ret.setId(id);
        ret.setBookId(bookId);
        ret.setChapterNumber(chapterNumber);
        ret.setName(name);
        ret.setDescription(description);
        ret.setText(value);
        return ret;
    }
}
