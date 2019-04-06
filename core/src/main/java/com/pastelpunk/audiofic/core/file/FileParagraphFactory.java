package com.pastelpunk.audiofic.core.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileParagraphFactory {

    public FileParagraphFactory(){
    }

    public List<Paragraph> create(String path, int bufferSize) throws Exception{
        List<Paragraph> paragraphs = new ArrayList<>();

        try(InputStream inputStream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream), bufferSize)){

            int offset = 0;
            while (br.ready()) {
                char[] buffer = new char[bufferSize];
                br.mark(bufferSize);

                int end = br.read(buffer, 0, bufferSize);
                int speechBreak = end - 1;
                for(; speechBreak>=0; speechBreak--){
                    if(isPunctuationBreak(buffer[speechBreak]))break;
                }

                //Check for spaces
                if(speechBreak <= 0) {
                    speechBreak = end - 1;
                    for (; speechBreak >= 0; speechBreak--) {
                        if (isBreak(buffer[speechBreak])) break;
                    }
                }

                //If nothing is found go to the end of the buffer
                if(speechBreak <= 0){
                    speechBreak = end;
                }

                Paragraph paragraph = new Paragraph(path, bufferSize, speechBreak, offset);
                paragraphs.add(paragraph);

                offset += speechBreak;
                br.reset();
                br.skip(speechBreak);
            }
        }

        return paragraphs;
    }

    private static boolean isBreak(char c){
        return  (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == '.');
    }

    private static boolean isPunctuationBreak(char c){
        return  (c == '?' || c == '!' || c == '.');
    }
}
