package com.pastelpunk.audiofic.app.activities.player;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.app.activities.AudioFicConstants;
import com.pastelpunk.audiofic.app.cache.BookProgress;
import com.pastelpunk.audiofic.app.cache.BookProgressCache;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepository;
import com.pastelpunk.audiofic.core.model.Book;
import com.pastelpunk.audiofic.core.model.Chapter;

import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class Player extends AppCompatActivity implements TextToSpeech.OnInitListener, BookPlayerCallback {

    @Inject
    ChapterRepository chapterRepository;

    private TextToSpeech textToSpeech;
    private BookPlayer bookPlayer;
    private Gson gson;
    private BookProgressCache bookProgressCache;
    private BookProgress bookProgress;

    private Button play;
    private Button back;
    private Button forward;
    private Button previousChapter;
    private Button nextChapter;

    public Player() {
        gson = new Gson();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        bookProgressCache = new BookProgressCache(this.getApplicationContext());

        if(Objects.nonNull(this.getIntent().getStringExtra(AudioFicConstants.SELECTED_BOOK))){
            String json = this.getIntent().getStringExtra(AudioFicConstants.SELECTED_BOOK);
            Book book = gson.fromJson(json, Book.class);
            if(bookProgressCache.hasBookBeenStarted(book.getId())){
                bookProgress = bookProgressCache.getBookProgress(book.getId());
            }else{
                bookProgress = new BookProgress(book.getId(), 0, 0);
            }
        }else{
            String json = this.getIntent().getStringExtra(AudioFicConstants.SELECTED_CHAPTER);
            Chapter chapter = gson.fromJson(json, Chapter.class);
            bookProgress = new BookProgress(chapter.getBookId(), chapter.getChapterNumber(), 0);
        }

        play = this.findViewById(R.id.button_play);
        back = this.findViewById(R.id.button_back);
        forward = this.findViewById(R.id.button_forward);
        previousChapter = this.findViewById(R.id.button_back_chapter);
        nextChapter = this.findViewById(R.id.button_forward_chapter);
        setButtonState(false);

        bookProgressCache.setBookProgress(bookProgress.getBookId(), bookProgress);
        textToSpeech = new TextToSpeech(this, this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        bookProgress = bookProgressCache.getBookProgress(bookProgress.getBookId());
        if(Objects.nonNull(bookPlayer)){
            bookPlayer.resume(bookProgress);
        }
    }

    @Override
    protected void onPause(){
        if(Objects.nonNull(bookPlayer)) {
            bookPlayer.pause();
            bookProgress = bookPlayer.getCurrentProgress();
            bookProgressCache.setBookProgress(bookProgress.getBookId(), bookProgress);
        }

        super.onPause();
    }

    @Override
    protected void onStop(){
        if(Objects.nonNull(bookPlayer)) {
            bookPlayer.pause();
            bookProgress = bookPlayer.getCurrentProgress();
            bookProgressCache.setBookProgress(bookProgress.getBookId(), bookProgress);
        }

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if(Objects.nonNull(textToSpeech)) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        super.onDestroy();
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(Player.class.toString(), "This Language is not supported");
            } else {
                bookPlayer = new DatabaseBookPlayer(textToSpeech, this, chapterRepository, bookProgress);
                textToSpeech.setOnUtteranceProgressListener(bookPlayer);
                setButtonState(true);
            }
        } else {
            Log.e(Player.class.toString(), "Init Failed");
        }
    }

    private void setButtonState(boolean enabled){
        play.setEnabled(enabled);
        back.setEnabled(enabled);
        forward.setEnabled(enabled);
        previousChapter.setEnabled(enabled);
        nextChapter.setEnabled(enabled);
    }

    public void onClick(View v) {

        if (Objects.nonNull(bookPlayer)) {
            if (play.equals(v)) {
                if ((bookPlayer.isPlaying())) {
                    bookPlayer.pause();
                } else {
                    bookPlayer.play();
                }
            } else if (back.equals(v)) {
                bookPlayer.previousParagraph();
            } else if (forward.equals(v)) {
                bookPlayer.nextParagraph();
            } else if(nextChapter.equals(v)){
                bookPlayer.nextChapter();
            }else if(previousChapter.equals(v)){
                bookPlayer.previousChapter(false);
            }
        }
    }



    @Override
    public void onBookFinished() {

        finish();
    }
}
