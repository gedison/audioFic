package com.pastelpunk.audiofic.core.download;

import com.pastelpunk.audiofic.core.model.Book;

import org.junit.Test;

import java.util.logging.Logger;

public class FanFicDotNetDownloaderTest {

    private static final Logger LOGGER = Logger.getLogger(FanFicDotNetDownloaderTest.class.toString());
    private FanFicDotNetDownloader downloader;

    public FanFicDotNetDownloaderTest(){
        downloader = new FanFicDotNetDownloader();
    }

    @Test
    public void ffdnTestMultiChapterTest() throws Exception{
        String uri = "https://www.fanfiction.net/s/11052712/2/Legacy-VI";
        Book book = downloader.downloadBook(uri);
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
        System.out.println(book.toString());
        book.getChapters().forEach(chapter -> {
            LOGGER.info(chapter.toString());
        });
    }

    @Test
    public void ffdnTestSingleChapterTest() throws Exception{
        String uri = "https://www.fanfiction.net/s/13084095/1/I-hate-myself";
        Book book = downloader.downloadBook(uri);
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
        System.out.println(book.toString());
        book.getChapters().forEach(chapter -> {
            LOGGER.info(chapter.toString());
        });
    }



}
