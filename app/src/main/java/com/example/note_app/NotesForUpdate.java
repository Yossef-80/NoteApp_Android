package com.example.note_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotesForUpdate {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotesForUpdate(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
