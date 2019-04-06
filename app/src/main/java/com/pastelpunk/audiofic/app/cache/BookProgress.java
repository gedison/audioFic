package com.pastelpunk.audiofic.app.cache;

public class BookProgress {

    private String bookId;
    private int chapter;
    private int paragraph;

    public BookProgress(String bookId, int chapter, int paragraph) {
        this.bookId = bookId;
        this.chapter = chapter;
        this.paragraph = paragraph;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        if(chapter<0){
            throw new IllegalArgumentException("Chapter must be greater than 0");
        }

        this.chapter = chapter;
    }

    public int getParagraph() {
        return paragraph;
    }

    public void setParagraph(int paragraph) {
        if(paragraph<0){
            throw new IllegalArgumentException("Paragraph must be greater than 0");
        }

        this.paragraph = paragraph;
    }
}
