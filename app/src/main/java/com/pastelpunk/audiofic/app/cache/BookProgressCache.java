package com.pastelpunk.audiofic.app.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Objects;

public class BookProgressCache {

    private Context context;
    private Gson gson = new Gson();

    public BookProgressCache(Context context){
        this.context = context;
    }

    public void setBookProgress(String bookId, BookProgress bookProgress){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AudioFic", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(bookId, gson.toJson(bookProgress)).apply();
    }

    public boolean hasBookBeenStarted(String bookId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AudioFic", Context.MODE_PRIVATE);
        return sharedPreferences.contains(bookId);
    }

    public BookProgress getBookProgress(String bookId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AudioFic", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(bookId, null);
        if(Objects.isNull(json)){
            return null;
        }else{
            return gson.fromJson(json, BookProgress.class);
        }
    }
}
