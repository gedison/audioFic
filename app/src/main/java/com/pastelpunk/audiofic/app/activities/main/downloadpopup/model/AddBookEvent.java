package com.pastelpunk.audiofic.app.activities.main.downloadpopup.model;

public class AddBookEvent {
    private SupportedType type;
    private String path;

    public AddBookEvent(SupportedType type, String path) {
        this.type = type;
        this.path = path;
    }

    public SupportedType getType() {
        return type;
    }

    public void setType(SupportedType type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
