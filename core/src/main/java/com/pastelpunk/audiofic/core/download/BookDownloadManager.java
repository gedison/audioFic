package com.pastelpunk.audiofic.core.download;

import com.pastelpunk.audiofic.core.model.Book;

public interface BookDownloadManager {

    Book downloadBook(String url) throws Exception;


}
