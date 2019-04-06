package com.pastelpunk.audiofic.app.database.chapter;

import com.pastelpunk.audiofic.core.model.Chapter;

import java.util.List;

public interface ChapterRepository {
    Chapter createChapter(String bookId, Chapter toCreate);
    Chapter updateChapter(String bookId, String chapterId, Chapter toUpdate);
    void deleteChapter(String bookId, String chapterId);
    List<Chapter> getAllChapters(String bookId);
    List<Chapter> getChapterTitles(String bookId);
    Chapter getChapter(String bookId, String chapterId);
    Chapter getIthChapter(String bookId, int chapterNumber);
}
