package com.pastelpunk.audiofic.core.model;

import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Book {
    private String id;
    private String name;
    private String author;
    private String description;
    private List<Chapter> chapters = new ArrayList<>();

    public Book(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void addChapter(Chapter chapterToAdd){
        this.chapters.add(chapterToAdd);
    }


    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
