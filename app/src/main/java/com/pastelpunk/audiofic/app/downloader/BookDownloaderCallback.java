package com.pastelpunk.audiofic.app.downloader;

import com.pastelpunk.audiofic.core.model.Book;

import java.util.List;

public interface BookDownloaderCallback {
    public void onDownloadComplete(List<Book> downloaded);
}
