package com.pastelpunk.audiofic.core.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class Paragraph {

    private String id;
    private String path;
    private int bufferSize;
    private int size;
    private int offset;

    public Paragraph(String path, int bufferSize, int size, int offset){
        this.id = UUID.randomUUID().toString();

        this.path = path;
        this.bufferSize = bufferSize;
        this.size = size;
        this.offset = offset;
    }

    public String getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public String getPath() {return path;}

    public int getBufferSize() {return bufferSize;}

    public String getText() throws Exception{
        try(InputStream inputStream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream), getBufferSize());
        ){
            char[] buffer = new char[getSize()];

            br.skip(getOffset());
            br.read(buffer, 0, getSize());
            return new String(buffer);
        }
    }
}
