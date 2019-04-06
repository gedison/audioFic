package com.pastelpunk.audiofic.app.activities.player;

import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.pastelpunk.audiofic.app.cache.BookProgress;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepository;
import com.pastelpunk.audiofic.core.model.Chapter;
import com.pastelpunk.audiofic.core.model.ParagraphFactory;

import java.util.List;
import java.util.Objects;

public class DatabaseBookPlayer extends BookPlayer  {

    private final static int BUFFER_SIZE = 512;

    private BookProgress bookProgress;
    private List<String> paragraphs;

    private TextToSpeech textToSpeech;
    private ChapterRepository chapterRepository;
    private BookPlayerCallback activity;

    public DatabaseBookPlayer(TextToSpeech textToSpeech,
                              BookPlayerCallback activity,
                              ChapterRepository chapterRepository,
                              BookProgress bookProgress){

        this.bookProgress = bookProgress;
        this.textToSpeech = textToSpeech;
        this.chapterRepository = chapterRepository;
        this.activity = activity;

        Chapter chapter = chapterRepository.getIthChapter(bookProgress.getBookId(), bookProgress.getChapter());
        if(Objects.nonNull(chapter)) {
            paragraphs = ParagraphFactory.create(chapter.getText(), BUFFER_SIZE);
        }else{
            activity.onBookFinished();
        }
    }

    private void speak(){
        textToSpeech.speak(paragraphs.get(bookProgress.getParagraph()),
                TextToSpeech.QUEUE_FLUSH,
                null,
                Integer.toString(bookProgress.getParagraph()));
    }

    @Override
    public boolean isPlaying(){
        return textToSpeech.isSpeaking();
    }

    @Override
    public void play() {
        if(!textToSpeech.isSpeaking()){
            speak();
        }
    }

    @Override
    public void pause() {
        if(textToSpeech.isSpeaking()){
            textToSpeech.stop();
        }
    }

    @Override
    public void previousChapter(boolean reset) {
        pause();

        int previousChapterIndex = bookProgress.getChapter() - 1;
        Chapter chapter = chapterRepository.getIthChapter(bookProgress.getBookId(), previousChapterIndex);
        if(Objects.nonNull(chapter)) {
            bookProgress.setChapter(previousChapterIndex);
            paragraphs = ParagraphFactory.create(chapter.getText(), BUFFER_SIZE);
            bookProgress.setParagraph((reset) ? 0 : (paragraphs.size() - 1));
            speak();
        }else{
            activity.onBookFinished();
        }
    }

    @Override
    public void nextChapter() {
        pause();

        int nextChapterIndex = bookProgress.getChapter() + 1;
        Chapter chapter = chapterRepository.getIthChapter(bookProgress.getBookId(), nextChapterIndex);
        if(Objects.nonNull(chapter)) {
            bookProgress.setChapter(nextChapterIndex);
            paragraphs = ParagraphFactory.create(chapter.getText(), BUFFER_SIZE);
            bookProgress.setParagraph(0);
            speak();
        }else{
            activity.onBookFinished();
        }
    }

    @Override
    public void nextParagraph() {
        pause();

        int nextParagraph = bookProgress.getParagraph() + 1;
        if(paragraphs.size()<=nextParagraph){
            nextChapter();
        }else{
            bookProgress.setParagraph(nextParagraph);
            speak();
        }
    }

    @Override
    public void previousParagraph() {
        pause();

        int previousParagraph = bookProgress.getParagraph() - 1;
        if(previousParagraph < 0){
            previousChapter(false);
        }else{
            bookProgress.setParagraph(previousParagraph);
            speak();
        }
    }

    @Override
    public int getParagraphs() {
        return paragraphs.size();
    }

    @Override
    public void setParagraph(int paragraph) {
        paragraph = Math.max(0, paragraph);
        paragraph = Math.min(paragraph, (paragraphs.size()-1));

        bookProgress.setParagraph(paragraph);
        speak();
    }

    @Override
    public void resume(BookProgress bookProgress) {
        this.bookProgress = bookProgress;

        Chapter chapter = chapterRepository.getIthChapter(bookProgress.getBookId(), bookProgress.getChapter());
        if(Objects.nonNull(chapter)) {
            paragraphs = ParagraphFactory.create(chapter.getText(), BUFFER_SIZE);
        }else{
            activity.onBookFinished();
        }
    }

    @Override
    public BookProgress getCurrentProgress() {
        return bookProgress;
    }

    @Override
    public void onStart(String utteranceId) {
        Log.d(DatabaseBookPlayer.class.toString(), String.format("Starting to play utterance %s", utteranceId));
    }

    @Override
    public void onDone(String utteranceId) {
        nextParagraph();
    }

    @Override
    public void onError(String utteranceId) {
        Log.e(DatabaseBookPlayer.class.toString(), String.format("Fuck I guess... %s", utteranceId));
    }
}
