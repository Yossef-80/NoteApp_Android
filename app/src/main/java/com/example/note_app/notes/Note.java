package com.example.note_app.notes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Note {
    /*
    private  int id;
    @SerializedName("title")
    @Expose
    private  String title;
    @SerializedName("body")
    @Expose
    private String details;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("dateCreated")
    @Expose
    private String DateCreated;
    @SerializedName("isDone")
    @Expose
    private Boolean isDone;
    @SerializedName("alarmDate")
    @Expose
    private String AlarmDate;
*/
    @SerializedName("isDone")
    @Expose
    private Boolean isDone;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("dateCreated")
    @Expose
    private String dateCreated;
    @SerializedName("dateModified")
    @Expose
    private String dateModified;
    @SerializedName("alarmDate")
    @Expose
    private Object alarmDate;
    @SerializedName("tag")
    @Expose
    private Object tag;

    public Note(String title, String body, Object alarmDate, Object tag) {
        this.title = title;
        this.body = body;
        this.alarmDate = alarmDate;
        this.tag = tag;
    }

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

    public String getDetails() {
        return body;
    }

    public void setDetails(String details) {
        this.body = details;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        dateCreated = dateCreated;
    }

    public Object getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }


    public Note( String id,String title, String details, String tag, String dateCreated, String alarmDate) {
        this.id=id;
        this.title = title;
        this.body = details;
        this.tag = tag;
        this.dateCreated = dateCreated;
        this.alarmDate = alarmDate;
    }

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Note(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
