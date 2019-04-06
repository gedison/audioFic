package com.pastelpunk.audiofic.core.model;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;


public class ParagraphTest {

    Logger LOGGER = Logger.getLogger(ParagraphTest.class.getName());

    private int bufferSize = 55;

    private static String getStringFromFile(String path) throws IOException{
        InputStream inputStream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder valueBuilder = new StringBuilder();
        while (br.ready()) {
            valueBuilder.append(br.readLine());
        }
        return valueBuilder.toString();
    }

    @Test
    public void test() throws Exception{
        String path = "../core/src/test/resources/txt-test.txt";


        ParagraphFactory paragraphFactory = new ParagraphFactory();
        List<String> paragraphs = paragraphFactory.create(getStringFromFile(path), bufferSize);

        StringBuilder result = new StringBuilder();
        for(String paragraph : paragraphs){
            result.append(paragraph).append("|||");
        }

        LOGGER.info(result.toString());
    }

    @Test
    public void testSingleWord() throws Exception{
        String path = "../core/src/test/resources/txt-test-single-word.txt";

        ParagraphFactory paragraphFactory = new ParagraphFactory();
        List<String> paragraphs = paragraphFactory.create(getStringFromFile(path), bufferSize);

        StringBuilder result = new StringBuilder();
        for(String paragraph : paragraphs){
            result.append(paragraph).append("|||");
        }

        LOGGER.info(result.toString());
    }
    @Test
    public void testStory() throws Exception{
        String path = "../core/src/test/resources/txt-story.txt";

        ParagraphFactory paragraphFactory = new ParagraphFactory();
        List<String> paragraphs = paragraphFactory.create(getStringFromFile(path), 100);

        StringBuilder result = new StringBuilder();
        for(String paragraph : paragraphs){
            result.append(paragraph).append("|||");
        }

        LOGGER.info(result.toString());
    }

}
