package com.pastelpunk.audiofic.core.download;

import com.pastelpunk.audiofic.core.model.Book;
import com.pastelpunk.audiofic.core.model.Chapter;


import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import us.codecraft.xsoup.Xsoup;

public class AO3Downloader implements BookDownloadManager {

    private static final Logger LOGGER = Logger.getLogger(AO3Downloader.class.toString());

    private static final String TITLE_XML = "//h2[contains(@class, 'title heading')]";
    private static final String AUTHOR_XML = "//h3[contains(@class, 'byline heading')]";
    private static final String CHAPTER_SUMMARY_XML = "//blockquote[contains(@class, 'userstuff')]";
    private static final String CHAPTER_LIST_ID = "selected_id";
    private static final String TEXT_XML = "//div[@id='chapters']/div[@class='userstuff']";
    private static final String TEXT_XML_CHAPTERED = "//div[@id='chapters']/div/div[@class='userstuff module']/p";

    public AO3Downloader(){ }

    @Override
    public Book downloadBook(String url) throws Exception{
        Book ret = new Book();

       String baseUri = url.substring(0, url.lastIndexOf('/'));
        //String chapterUri  = baseUri + "?view_adult=true";
        String uri = url;



URL test = new URL(uri);
URLConnection connection = test.openConnection();
        connection.setRequestProperty("Cookie", "accepted_tos=20180523; _otwarchive_session=dklpYzh1NXdCS3ZDR2Z4OVF5aWEwZkNETG9ZaTdvNllGZE9pdVBEUHRPZjRWYU80aTdWT2M3dDJJWkVDK0lTeUU2cjBmczIybGdQYm8yRUlCeWY1ZEdOeC9xWE1iQzk1Mk9CbmlJbVJhSUk5S3Jlb1NtY2JjZzZnbGR1bEI2K1l0RFN5ZWdWWjA1ZUhRMEU3eVpaK0JhdW1QMzc5ek1YaldYTjNxa2JpeXQzdmRCZWdTOUdXeW10QnIxelRiYk92U3BMN1R1b2dwK0g3WGdSWDUvdndvNnpwT21sM1FmS1FpbmFKNkUxR2p2YmpKbks2a3ZiU1NTSitrN1JuRi9TWTE4em8rdWN4ajQ3YzlBZno4NEE3Ym1ZOHFhaVV2cDZNekVyZHBxRjlCM1k9LS1ZY3c3dWVRM2oyNnZQeHdVczFLN3NnPT0%3D--55d13e8f1acf146bb59c3097b6fd697d21d7796a");


        String out = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();


        Document document = Jsoup.parse(out);

        ret.setName(Xsoup.compile(TITLE_XML).evaluate(document).getElements().text());
        ret.setAuthor(Xsoup.compile(AUTHOR_XML).evaluate(document).getElements().text());

        Element select = document.getElementById(CHAPTER_LIST_ID);
        if(Objects.nonNull(select)){
            Elements elements = select.getAllElements();
            List<String> ids = elements.stream().map(Element::val).distinct().filter(StringUtils::isNotBlank).collect(Collectors.toList());
            ids.forEach(chapterId->{
                try {
                    ret.addChapter(downloadChapter(baseUri + "/" + chapterId , false));
                }catch (Exception e){
                    LOGGER.warning(e.getMessage());
                }
            });
        }else{
            try {
                if(url.contains("chapters")){
                    ret.addChapter(downloadChapter(url , false));
                }else{
                    ret.addChapter(downloadChapter(url , true));
                }
            }catch (Exception e){
                LOGGER.warning(e.getMessage());
            }
        }

        return ret;
    }

    private Chapter downloadChapter(String uri, boolean isSingleChapter) throws Exception{
        Chapter ret = new Chapter();

        URL test = new URL(uri);
        URLConnection connection = test.openConnection();
        connection.setRequestProperty("Cookie", "accepted_tos=20180523; _otwarchive_session=dklpYzh1NXdCS3ZDR2Z4OVF5aWEwZkNETG9ZaTdvNllGZE9pdVBEUHRPZjRWYU80aTdWT2M3dDJJWkVDK0lTeUU2cjBmczIybGdQYm8yRUlCeWY1ZEdOeC9xWE1iQzk1Mk9CbmlJbVJhSUk5S3Jlb1NtY2JjZzZnbGR1bEI2K1l0RFN5ZWdWWjA1ZUhRMEU3eVpaK0JhdW1QMzc5ek1YaldYTjNxa2JpeXQzdmRCZWdTOUdXeW10QnIxelRiYk92U3BMN1R1b2dwK0g3WGdSWDUvdndvNnpwT21sM1FmS1FpbmFKNkUxR2p2YmpKbks2a3ZiU1NTSitrN1JuRi9TWTE4em8rdWN4ajQ3YzlBZno4NEE3Ym1ZOHFhaVV2cDZNekVyZHBxRjlCM1k9LS1ZY3c3dWVRM2oyNnZQeHdVczFLN3NnPT0%3D--55d13e8f1acf146bb59c3097b6fd697d21d7796a");

        String out = new Scanner(connection.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        Document document = Jsoup.parse(out);
        ret.setDescription(Xsoup.compile(CHAPTER_SUMMARY_XML).evaluate(document).getElements().text());
        ret.setText(Xsoup.compile((isSingleChapter) ? TEXT_XML : TEXT_XML_CHAPTERED).evaluate(document).getElements().text());
        return ret;
    }
}
