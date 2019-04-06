package com.pastelpunk.audiofic.app.activities.player;

import com.pastelpunk.audiofic.app.activities.main.Main;
import com.pastelpunk.audiofic.app.activities.main.book.BookFragment;
import com.pastelpunk.audiofic.app.activities.main.booklist.BookListFragment;
import com.pastelpunk.audiofic.app.database.book.BookRepository;
import com.pastelpunk.audiofic.app.database.book.BookRepositoryImpl;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepository;
import com.pastelpunk.audiofic.app.database.chapter.ChapterRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class PlayerModule {

    @ContributesAndroidInjector
    abstract Player contributeActivityInjector();

}

