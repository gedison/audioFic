package com.pastelpunk.audiofic.app.downloader;

import android.os.AsyncTask;
import android.util.Log;

import com.pastelpunk.audiofic.core.download.BookDownloadManager;
import com.pastelpunk.audiofic.core.model.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookDownloader extends AsyncTask<String, Void, List<Book>> {

    private BookDownloaderCallback activity;
    private BookDownloadManager bookDownloadManager;

    public BookDownloader(BookDownloaderCallback activity, BookDownloadManager bookDownloadManager){
        this.activity = activity;
        this.bookDownloadManager = bookDownloadManager;
    }

    @Override
    protected List<Book> doInBackground(String... bookUrls) {
        List<Book> ret = new ArrayList<>();
        Arrays.asList(bookUrls).forEach(url ->{
            try {
                ret.add(bookDownloadManager.downloadBook(url));
            }catch (Exception e){
                Log.e(BookDownloader.class.toString(), String.format("Failed to download %s", url), e);
            }
        });

        return ret;
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        activity.onDownloadComplete(books);
    }
}
