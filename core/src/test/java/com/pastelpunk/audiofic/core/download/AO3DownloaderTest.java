package com.pastelpunk.audiofic.core.download;

import com.pastelpunk.audiofic.core.model.Book;

import org.junit.Test;

import java.util.logging.Logger;

public class AO3DownloaderTest {

    private static final Logger LOGGER = Logger.getLogger(AO3DownloaderTest.class.toString());
    private AO3Downloader downloader;

    public AO3DownloaderTest(){
        downloader = new AO3Downloader();
    }

    @Test
    public void ao3TestMultiChapterTest() throws Exception{
        String uri = "https://archiveofourown.org/works/14489991/chapters/33473313";
        Book book = downloader.downloadBook(uri);
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
    }

    @Test
    public void ao3TestMultiChapterTest2() throws Exception{
        String uri = "https://archiveofourown.org/works/1047968/chapters/2095960";
        Book book = downloader.downloadBook(uri);
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
    }

    @Test
    public void ao3TestSingleChapterTest() throws Exception{
        String uri = "https://archiveofourown.org/works/15982187";
        Book book = downloader.downloadBook(uri);
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
    }

    @Test
    public void ao3TestMultiChapterTest4() throws Exception{
        String uri = "https://archiveofourown.org/works/12235209/chapters/27797637";
       // String uri = "https://archiveofourown.org/works/12235209/chapters/a";
        Book book = downloader.downloadBook(uri);
        LOGGER.info(book.getName());
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
    }

    @Test
    public void ao3TestSingleChapterTest3() throws Exception{
        String uri = "https://archiveofourown.org/works/16006598/chapters/37348277";
        Book book = downloader.downloadBook(uri);
        book.getChapters().forEach(chapter -> LOGGER.info(chapter.getText()));
    }
}
