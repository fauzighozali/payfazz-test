package com.example.newshacker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNews {
    @SerializedName("by")
    private String by;
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("time")
    private String time;

    public ModelNews(String by, int id, String title, String time) {
        this.by = by;
        this.id = id;
        this.title = title;
        this.time = time;
    }

    public ModelNews() {
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
