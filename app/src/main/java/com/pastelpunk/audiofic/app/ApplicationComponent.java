package com.pastelpunk.audiofic.app;

import com.pastelpunk.audiofic.app.activities.main.Main;
import com.pastelpunk.audiofic.app.activities.main.MainModule;
import com.pastelpunk.audiofic.app.activities.main.booklist.BookListFragment;
import com.pastelpunk.audiofic.app.activities.player.PlayerModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = { AndroidInjectionModule.class, ApplicationModule.class, MainModule.class, PlayerModule.class})
public interface ApplicationComponent extends AndroidInjector<AudioFic> {
}
