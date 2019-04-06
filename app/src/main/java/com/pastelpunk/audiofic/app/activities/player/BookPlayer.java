package com.pastelpunk.audiofic.app.activities.player;

import android.speech.tts.UtteranceProgressListener;

import com.pastelpunk.audiofic.app.cache.BookProgress;

public abstract class BookPlayer extends UtteranceProgressListener {
    abstract boolean isPlaying();
    abstract void play();
    abstract void pause();
    abstract void previousChapter(boolean reset);
    abstract void nextChapter();
    abstract void nextParagraph();
    abstract void previousParagraph();
    abstract int getParagraphs();
    abstract void setParagraph(int i);
    abstract void resume(BookProgress bookProgress);
    abstract BookProgress getCurrentProgress();
}
