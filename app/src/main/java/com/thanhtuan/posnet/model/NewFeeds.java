package com.thanhtuan.posnet.model;

public class NewFeeds {
    private String Type;
    private String Title;
    private String content;

    public NewFeeds() {
    }

    public NewFeeds(String type, String title, String content) {
        Type = type;
        Title = title;
        this.content = content;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
