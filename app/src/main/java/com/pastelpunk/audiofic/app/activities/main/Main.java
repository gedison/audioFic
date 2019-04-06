package com.pastelpunk.audiofic.app.activities.main;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pastelpunk.audiofic.app.R;
import com.pastelpunk.audiofic.app.activities.AudioFicConstants;
import com.pastelpunk.audiofic.app.activities.main.book.BookFragment;
import com.pastelpunk.audiofic.app.activities.main.book.ChapterSelectionCallback;
import com.pastelpunk.audiofic.app.activities.main.booklist.BookListFragment;
import com.pastelpunk.audiofic.app.activities.main.booklist.BookSelectionCallback;
import com.pastelpunk.audiofic.app.activities.main.downloadpopup.AddBookCallback;
import com.pastelpunk.audiofic.app.activities.main.downloadpopup.AddBookDialogFragment;
import com.pastelpunk.audiofic.app.activities.main.downloadpopup.model.AddBookEvent;
import com.pastelpunk.audiofic.app.activities.player.Player;
import com.pastelpunk.audiofic.app.database.book.BookRepository;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepository;
import com.pastelpunk.audiofic.app.downloader.BookDownloader;
import com.pastelpunk.audiofic.app.downloader.BookDownloaderCallback;
import com.pastelpunk.audiofic.core.download.AO3Downloader;
import com.pastelpunk.audiofic.core.download.FanFicDotNetDownloader;
import com.pastelpunk.audiofic.core.model.Book;
import com.pastelpunk.audiofic.core.model.Chapter;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class Main extends AppCompatActivity implements BookDownloaderCallback, BookSelectionCallback,
        ChapterSelectionCallback, AddBookCallback {

    private static final String FRAGMENT_TAG_BOOK_LIST = "fragmentTagBookList";
    private static final String FRAGMENT_TAG_BOOK = "fragmentTagBook";

    @Inject
    BookRepository bookRepository;
    @Inject
    ChapterRepository chapterRepository;

    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(BookListFragment.newInstance(), FRAGMENT_TAG_BOOK_LIST);

        bookRepository.deleteEmptyBooks();
    }

    private void setFragment(Fragment toDisplay, String tag){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(Objects.nonNull(fragmentManager.findFragmentById(R.id.fragment_main))){
            fragmentTransaction.replace(R.id.fragment_main, toDisplay, tag);
            fragmentTransaction.addToBackStack(null);
        }else{
            fragmentTransaction.add(R.id.fragment_main, toDisplay, tag);
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBookSelection(Book selected) {
        Log.d(Main.class.toString(), selected.toString());
        setFragment(BookFragment.newInstance(selected), FRAGMENT_TAG_BOOK);
    }

    @Override
    public void onResume(Book selected) {
        Log.d(Main.class.toString(), selected.toString());

        Intent intent = new Intent(this, Player.class);
        intent.putExtra(AudioFicConstants.SELECTED_BOOK, new Gson().toJson(selected));
        startActivity(intent);
    }

    @Override
    public void onChapterSelection(Chapter selected) {
        Log.d(Main.class.toString(), selected.toString());

        Intent intent = new Intent(this, Player.class);
        intent.putExtra(AudioFicConstants.SELECTED_CHAPTER, new Gson().toJson(selected));
        startActivity(intent);
    }

    public void showDownloader(View v){
        DialogFragment dialog = new AddBookDialogFragment();
        dialog.show(getFragmentManager(), "AddBookDialogFragment");
    }

    @Override
    public void onBookChosen(AddBookEvent event) {
        switch (event.getType()){
            case AO3:{
                downloadBookFromAO3(event);
                break;
            }

            case FANFIC:{
                downloadBookFromFanFic(event);
                break;
            }
            default:{
                Toast.makeText(this, "Type not supported", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadBookFromAO3(AddBookEvent addBookEvent){
        String url = addBookEvent.getPath();
        BookDownloader bookDownloader = new BookDownloader(this, new AO3Downloader());
        bookDownloader.execute(url);
    }

    private void downloadBookFromFanFic(AddBookEvent addBookEvent){
        String url = addBookEvent.getPath();
        BookDownloader bookDownloader = new BookDownloader(this, new FanFicDotNetDownloader());
        bookDownloader.execute(url);
    }

    @Override
    public void onDownloadComplete(List<Book> downloaded) {
        downloaded.forEach(book->{
            Log.d(Main.class.toString(), book.toString());
            bookRepository.createBook(book);
            book.getChapters().forEach(chapter -> {
                chapter.setChapterNumber(book.getChapters().indexOf(chapter));
                chapterRepository.createChapter(book.getId(), chapter);
            });

            BookListFragment fragment = (BookListFragment) getFragmentManager().findFragmentByTag(FRAGMENT_TAG_BOOK_LIST);
            if (fragment != null && fragment.isVisible()) {
                fragment.resetList();
            }
        });
    }
}
