package com.pastelpunk.audiofic.core.download;

import com.pastelpunk.audiofic.core.model.Book;
import com.pastelpunk.audiofic.core.model.Chapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

import us.codecraft.xsoup.Xsoup;

public class FanFicDotNetDownloader implements BookDownloadManager {

    private static final Logger LOGGER = Logger.getLogger(FanFicDotNetDownloader.class.toString());

    private static final String TITLE_XML = "/html/body/div[@id='content_parent']/div[@id='content_wrapper']/div[@id='content_wrapper_inner']/div[@id='profile_top']/b[@class='xcontrast_txt']";
    private static final String AUTHOR_XML = "/html/body/div[@id='content_parent']/div[@id='content_wrapper']/div[@id='content_wrapper_inner']/div[@id='profile_top']/a[@class='xcontrast_txt'][1]";
    private static final String CHAPTER_SUMMARY_XML = "/html/body/div[@id='content_parent']/div[@id='content_wrapper']/div[@id='content_wrapper_inner']/span/select[@id='chap_select']";
    private static final String CHAPTER_LIST_ID = "chap_select";

    private static final String TEXT_XML = "/html/body/div[@id='content_parent']/div[@id='content_wrapper']/div[@id='content_wrapper_inner']/div[@id='storytextp']/div[@id='storytext']";
    private static final String TEXT_XML_CHAPTERED = "/html/body/div[@id='content_parent']/div[@id='content_wrapper']/div[@id='content_wrapper_inner']/div[@id='storytextp']/div[@id='storytext']";


    @Override
    public Book downloadBook(String url) throws Exception{
        Book ret = new Book();

        String baseUri = url;
        String out = new Scanner(new URL(url).openStream(), "UTF-8").useDelimiter("\\A").next();
        Document document = Jsoup.parse(out);

        List<Node>nodes = document.childNodes();
        for(Node node : nodes){
            if(!(node instanceof DocumentType)){
                document = Jsoup.parse(node.outerHtml());
            }
        }

        ret.setName(Xsoup.compile(TITLE_XML).evaluate(document).getElements().text());
        ret.setAuthor(Xsoup.compile(AUTHOR_XML).evaluate(document).getElements().text());

        Element select = document.getElementById(CHAPTER_LIST_ID);
        if(Objects.nonNull(select)){
            int numberOfChapters = select.getAllElements().size();
            for(int index = 1; index<numberOfChapters; index++){
                String toDownload = getChapterUrl(baseUri, index);
                try {
                    ret.addChapter(downloadChapter(index, toDownload));
                }catch (Exception e){
                    LOGGER.warning(e.getMessage());
                }
            }
        }else{
            try {
                ret.addChapter(downloadChapter(1, url));
            }catch (Exception e){
                LOGGER.warning(e.getMessage());
            }
        }

        return ret;
    }

    private String getChapterUrl(String url, int chapterIndex){
        String[] array = url.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<array.length; i++){
            String temp = array[i];
            if(i == 5){
                temp = Integer.toString(chapterIndex);
            }

            stringBuilder.append(temp+"/");
        }
        return stringBuilder.toString();
    }

    private Chapter downloadChapter(int chapterIndex, String uri) throws Exception{
        Chapter ret = new Chapter();
        String out = new Scanner(new URL(uri).openStream(), "UTF-8").useDelimiter("\\A").next();
        Document document = Jsoup.parse(out);

        List<Node>nodes = document.childNodes();
        for(Node node : nodes){
            if(!(node instanceof DocumentType)){
                document = Jsoup.parse(node.outerHtml());
            }
        }

        ret.setDescription("");
        ret.setText(Xsoup.compile(TEXT_XML).evaluate(document).getElements().text());
        ret.setChapterNumber(chapterIndex);
        return ret;
    }
}
