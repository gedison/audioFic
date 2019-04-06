package com.pastelpunk.audiofic.app.activities.main.book;

import com.pastelpunk.audiofic.core.model.Book;
import com.pastelpunk.audiofic.core.model.Chapter;

public interface ChapterSelectionCallback {
    void onResume(Book selected);
    void onChapterSelection(Chapter selected);
}
