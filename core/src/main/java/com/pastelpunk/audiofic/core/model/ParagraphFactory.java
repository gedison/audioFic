package com.pastelpunk.audiofic.core.model;

import java.util.ArrayList;
import java.util.List;

public class ParagraphFactory {


    public ParagraphFactory() {
    }

    public static List<String> create(String chapterText, int bufferSize){
        List<String> paragraphs = new ArrayList<>();

        int offset = 0;
        int end;

        while (offset < chapterText.length()) {
            end = offset + bufferSize;

            int speechBreak;
            if (end >= chapterText.length()) {
                speechBreak = chapterText.length();
            } else {
                speechBreak = end - 1;

                for (; speechBreak >= offset; speechBreak--) {
                    if (isPunctuationBreak(chapterText.charAt(speechBreak))) {
                        speechBreak++;
                        break;
                    }
                }

            }

            //Check for spaces
            if (speechBreak <= offset && speechBreak != chapterText.length()) {
                speechBreak = end - 1;
                for (; speechBreak >= offset; speechBreak--) {
                    if (isBreak(chapterText.charAt(speechBreak))) {
                        speechBreak++;
                        break;
                    }
                }
            }

            //If nothing is found go to the end of the buffer
            if (speechBreak < offset && speechBreak != chapterText.length()) {
                speechBreak = end;
            }

            String v = chapterText.substring(offset, speechBreak);
            paragraphs.add(v);
            offset = speechBreak;
        }

        return paragraphs;
    }

    private static boolean isBreak(char c) {
        return (c == ' ' || c == '\r' || c == '\n' || c == '\t');
    }

    private static boolean isPunctuationBreak(char c) {
        return (c == '?' || c == '!' || c == '.');
    }
}
