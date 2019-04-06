package com.pastelpunk.audiofic.core.file;

import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

public class ParagraphTest {

    Logger LOGGER = Logger.getLogger(ParagraphTest.class.getName());

    private int bufferSize = 55;

    @Test
    public void test() throws Exception{
        String path = "../core/src/test/resources/txt-test.txt";

        FileParagraphFactory paragraphFactory = new FileParagraphFactory();
        List<Paragraph> paragraphs = paragraphFactory.create(path, bufferSize);

        StringBuilder result = new StringBuilder();
        for(Paragraph paragraph : paragraphs){
            result.append(paragraph.getText()).append("|||");
        }

        LOGGER.info(result.toString());
    }

    @Test
    public void testSingleWord() throws Exception{
        String path = "../core/src/test/resources/txt-test-single-word.txt";

        FileParagraphFactory paragraphFactory = new FileParagraphFactory();
        List<Paragraph> paragraphs = paragraphFactory.create(path, bufferSize);

        StringBuilder result = new StringBuilder();
        for(Paragraph paragraph : paragraphs){
            result.append(paragraph.getText()).append("|||");
        }

        LOGGER.info(result.toString());
    }
    @Test
    public void testStory() throws Exception{
        String path = "../core/src/test/resources/txt-story.txt";

        FileParagraphFactory paragraphFactory = new FileParagraphFactory();
        List<Paragraph> paragraphs = paragraphFactory.create(path, 2048);

        StringBuilder result = new StringBuilder();
        for(Paragraph paragraph : paragraphs){
            result.append(paragraph.getText()).append("|||");
        }

        LOGGER.info(result.toString());
    }

}
