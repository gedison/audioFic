package com.pastelpunk.audiofic.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.pastelpunk.audiofic.app.database.AudioFicDBHelper;
import com.pastelpunk.audiofic.app.database.book.BookRepository;
import com.pastelpunk.audiofic.app.database.book.BookRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    public Context context(){
        return application;
    }

    @Provides
    @Singleton
    public SQLiteOpenHelper sqLiteOpenHelper(Context context){
        return new AudioFicDBHelper(context);
    }

}
