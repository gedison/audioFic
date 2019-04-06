package com.pastelpunk.audiofic.app.activities.main;

import com.pastelpunk.audiofic.app.activities.main.book.BookFragment;
import com.pastelpunk.audiofic.app.activities.main.booklist.BookListFragment;
import com.pastelpunk.audiofic.app.activities.main.downloadpopup.AddBookDialogFragment;
import com.pastelpunk.audiofic.app.database.book.BookRepository;
import com.pastelpunk.audiofic.app.database.book.BookRepositoryImpl;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepository;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @ContributesAndroidInjector
    abstract Main contributeActivityInjector();

    @ContributesAndroidInjector
    abstract BookListFragment contributeBookListFragment();

    @ContributesAndroidInjector
    abstract BookFragment contributeBookFragment();

    @ContributesAndroidInjector
    abstract AddBookDialogFragment contributeAddBookFragment();

    @Binds
    public abstract BookRepository bookRepository(BookRepositoryImpl bookRepositoryImpl);

    @Binds
    public abstract ChapterRepository chapterRepository(ChapterRepositoryImpl chapterRepositoryImpl);
}

