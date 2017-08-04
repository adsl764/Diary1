package com.park1993.diary1;

/**
 * Created by alfo6-2 on 2017-07-14.
 */

public class Item {

    private String query;
    private String content;
    private String imgUri;


    public Item(String query) {
        this.query = query;
    }

    public Item(String query, String content) {
        this.query = query;
        this.content = content;
    }

    public Item(String query, String content, String imgUri) {
        this.query = query;
        this.content = content;
        this.imgUri = imgUri;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
