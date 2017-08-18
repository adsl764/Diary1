package com.park1993.diary1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alfo6-2 on 2017-07-14.
 */

public class Item implements Parcelable{

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

    protected Item(Parcel in) {
        query = in.readString();
        content = in.readString();
        imgUri = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(query);
        dest.writeString(content);
        dest.writeString(imgUri);
    }
}
